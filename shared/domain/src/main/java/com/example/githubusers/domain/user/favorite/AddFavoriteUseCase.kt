package com.example.githubusers.domain.user.favorite

import com.example.githubusers.domain.usecase.CoroutineUseCase
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : CoroutineUseCase<AddFavoriteUseCase.Params, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Params) {
        userRepository.addFavorite(parameters.userName, parameters.avatarUrl)
    }

    data class Params(
        val userName: String,
        val avatarUrl: String,
    )
}