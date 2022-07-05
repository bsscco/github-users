package com.example.githubusers.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MviReducer<Event : UiEvent, State : UiState, Effect : UiEffect>(
    private val viewModelScope: CoroutineScope,
    initialState: State,
    private val handleEvent: (Event) -> Unit,
) {
    /**
     * 왜 UI 상태 Flow에 StateFlow를 쓰나요?
     *
     * 초기값(O)
     * Hot스트림(O) : 구독 시점부터 방출된 값만 수집할 수 있다.
     * distinctUntilChanged(O) : 같은 값이 연속방출 되면 뒤에 오는 값은 무시한다.
     * 마지막 방출된 1개의 값을 캐시하며 자원공유에 효과적이다.
     *
     * View는 상태값이 있어야 화면을 그릴 수 있다. StateFlow에는 생성 시점에 상태값을 넣을 수 있다.
     * View는 같은 상태값이 연속적으로 들어온다고 해서 UI를 갱신할 필요가 없다. StateFlow는 distinctUntilChanged가 기본적용 돼있다.
     * View는 최신의 상태값만 필요하며, View가 Flow를 다시 구독해도 최신 상태값을 수집할 수 있어야 한다. StateFlow는 마지막 방출된 1개 값을 캐시한다.
     * 여러 View가 동시에 최신값을 구독할 수 있어야 한다. StateFlow는 마지막 방출된 1개 값을 캐시한다.
     */
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow() // Read-only로 변경

    /**
     * 왜 UI 이벤트 Flow에 SharedFlow를 쓰나요?
     *
     * 초기값(X)
     * Hot스트림(O)
     * distinctUntilChanged(X)
     * SharedFlow 생성 시 replay:0으로 설정하면 아무 값도 캐시하지 않는다.
     *
     * UI 이벤트가 발생하기 전까지는 아무 동작을 할 필요가 없다. SharedFlow는 초기값을 넣지 않아도 된다.
     * 같은 위치에서 클릭이벤트가 연속으로 발생하면 중복무시 없이 순서대로 처리돼야 한다. SharedFlow는 distinctUntilChanged가 적용돼있지 않다.
     * Flow를 다시 구독해도 구독 전에 방출된 이벤트가 다시 수집되지 않아야 한다. replay:0인 SharedFlow는 마지막 방출된 값을 캐시하지 않는다.
     */
    private val _eventFlow = MutableSharedFlow<Event>()

    /**
     * 왜 사이드이펙트 Flow에 Channel을 쓰나요?
     *
     * 초기값(X)
     * Hot스트림(0)
     * distinctUntilChanged(X)
     * 수집되지 않은 값은 Channel에 쌓여있으며 수집돼야 비로소 값이 사라진다.
     * 방출된 값을 구독 중인 구독자 중에 한 구독자만 수집할 수 없다.
     *
     * 사이드이펙트가 발생하기 전까지는 아무 동작을 할 필요가 없다. Channel은 초기값을 넣지 않아도 된다.
     * 같은 사이드이펙트가 연속으로 발행되면 중복무시 없이 순서대로 처리돼야 한다. Channel은 distinctUntilChanged가 적용돼있지 않다.
     * 사이드이펙트는 누락 없이 오직 한 번만 처리돼야 한다. Channel의 값은 수집되기 전까지 쌓이며, 한 번 수집되면 이미 구독 중인 다른 구독자에게 수집되지 않는다.
     */
    private val _effectFlow = Channel<Effect>()
    val effectFlow = _effectFlow.receiveAsFlow()

    init {
        handleEvent()
    }

    private fun handleEvent() {
        _eventFlow
            .onEach { event -> handleEvent(event) }
            .launchIn(viewModelScope)
    }

    fun setState(reduce: suspend State.() -> State) {
        viewModelScope.launch {
            _stateFlow.emit(_stateFlow.value.reduce())
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effectFlow.send(effect)
        }
    }
}