package com.example.githubusers.domain.user.favorite

import com.example.githubusers.domain.usecase.CoroutineUseCase
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : CoroutineUseCase<RemoveFavoriteUseCase.Params, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Params) {
        userRepository.removeFavorite(parameters.userName)
    }

    data class Params(val userName: String)
}