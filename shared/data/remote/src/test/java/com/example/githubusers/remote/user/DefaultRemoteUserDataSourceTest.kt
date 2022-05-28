package com.example.githubusers.remote.user

import com.example.githubusers.data.user.search.DataSearchUser
import com.example.githubusers.data.user.search.DataSearchUserRateLimitedPerMinuteException
import com.example.githubusers.data.user.search.DataSearchUserTimeOutException
import com.example.githubusers.remote.user.search.SearchUsersResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException

class DefaultRemoteUserDataSourceTest {

    @Test
    fun `searchUser should throw DataSearchUserRateLimitedPerMinuteException`() = runTest {
        // Given
        val httpException = mockk<HttpException> {
            every { code() } returns 403
        }
        val apiService = FakeGithubApiService(httpException = httpException)
        val remoteUserDataSource = DefaultRemoteUserDataSource(apiService)

        // When
        val responseOrError = try {
            remoteUserDataSource.searchUsers("bsscco", perPage = 10, page = 1)
        } catch (error: Exception) {
            error
        }

        // Then
        assertEquals(true, responseOrError is DataSearchUserRateLimitedPerMinuteException)
    }

    @Test
    fun `searchUser should throw DataSearchUserTimeOutException`() = runTest {
        // Given
        val response = mockk<SearchUsersResponse> {
            every { incompleteResults } returns true
        }
        val apiService = FakeGithubApiService(searchUsersResponse = response)
        val remoteUserDataSource = DefaultRemoteUserDataSource(apiService)

        // When
        val responseOrError = try {
            remoteUserDataSource.searchUsers("bsscco", perPage = 10, page = 1)
        } catch (error: Exception) {
            error
        }

        // Then
        assertEquals(true, responseOrError is DataSearchUserTimeOutException)
    }

    @Test
    fun `searchUser should return list of DataSearchUser`() = runTest {
        // Given
        val bsscco = DataSearchUser(
            userName = "bsscco",
            avatarUrl = "url1",
        )
        val response = SearchUsersResponse(
            totalCount = 2,
            incompleteResults = false,
            items = listOf(
                SearchUsersResponse.Item(
                    login = bsscco.userName,
                    avatarUrl = bsscco.avatarUrl,
                )
            ),
        )
        val apiService = FakeGithubApiService(searchUsersResponse = response)
        val remoteUserDataSource = DefaultRemoteUserDataSource(apiService)

        // When
        val searchedUsers = remoteUserDataSource.searchUsers("bsscco", perPage = 10, page = 1)

        // Then
        assertEquals(listOf(bsscco), searchedUsers)
    }
}