package com.example.githubusers.domain.user.favorite

import com.example.githubusers.domain.user.FakeUserRepository
import com.example.githubusers.ktutil.flow.firstSuccessOrThrow
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

/**
 * 참고 : https://developer.android.com/kotlin/coroutines/test?hl=ko
 * 참고 : https://developer.android.com/kotlin/flow/test?hl=ko
 */
class LoadFavoritesUserCaseTest {

    @Test
    fun `LoadFavoritesUseCase Params All should return all favorites`() = runBlocking {
        // Given
        val apple1Favorite = Favorite("apple1", "url1")
        val banana1Favorite = Favorite("banana1", "url2")
        val userRepository = FakeUserRepository(favorites = mutableListOf(apple1Favorite, banana1Favorite))
        val loadFavoritesUseCase = LoadFavoritesUseCase(UnconfinedTestDispatcher(), userRepository)

        // When
        val favorites = loadFavoritesUseCase(LoadFavoritesUseCase.Params.All).firstSuccessOrThrow()

        // Then
        assertEquals(userRepository.favorites, favorites)
    }

    @Test
    fun `LoadFavoritesUseCase Params OnlySearched shared return searched favorites`() = runBlocking {
        // Given
        val apple1Favorite = Favorite("apple1", "url1")
        val banana1Favorite = Favorite("banana1", "url2")
        val userRepository = FakeUserRepository(favorites = mutableListOf(apple1Favorite, banana1Favorite))
        val loadFavoritesUseCase = LoadFavoritesUseCase(UnconfinedTestDispatcher(), userRepository)
        val keyword = "app"

        // When
        val favorites = loadFavoritesUseCase(LoadFavoritesUseCase.Params.OnlySearched(keyword)).firstSuccessOrThrow()

        // Then
        assertEquals(listOf(apple1Favorite), favorites)
    }
}

