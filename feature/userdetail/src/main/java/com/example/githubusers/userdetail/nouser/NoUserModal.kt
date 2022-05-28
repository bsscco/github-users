package com.example.githubusers.userdetail.nouser

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubusers.designsys.modal.AlertModal

@Composable
internal fun NoUserModal(
    state: NoUserContract.State,
    onEvent: (NoUserContract.Event) -> Unit,
) {
    AlertModal(
        state = AlertModal.State(
            visible = state.showModal,
            content = "유저가 없습니다.",
            primaryButtonText = "확인",
        ),
        onEvent = { event ->
            when (event) {
                is AlertModal.Event.OnPrimaryClick -> onEvent(NoUserContract.Event.OnOkClicked)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NoUserModal(
        state = NoUserContract.State(
            showModal = true,
        ),
        onEvent = {},
    )
}