package com.example.githubusers.usersearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.aosutil.dimention.textDp
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.domain.user.search.SearchUserRateLimitedPerMinuteException
import com.example.githubusers.domain.user.search.SearchUserTimeOutException

@Composable
fun ErrorMessage(error: Throwable) {
    val errorMessage = when (error) {
        is SearchUserRateLimitedPerMinuteException -> "1분 동안 10회 검색 제한에 의해 더 이상 데이터를 불러올 수 없습니다."
        is SearchUserTimeOutException -> "서버가 응답하지 않아 더 이상 데이터를 불러올 수 없습니다."
        else -> "알 수 없는 오류로 인해 더 이상 데이터를 불러올 수 없습니다."
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            fontSize = 10.textDp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RateLimitedPreview() {
    GithubUsersTheme {
        ErrorMessage(
            error = SearchUserRateLimitedPerMinuteException,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeOutPreview() {
    GithubUsersTheme {
        ErrorMessage(
            error = SearchUserTimeOutException,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnknownErrorPreview() {
    GithubUsersTheme {
        ErrorMessage(
            error = Exception("Unknown error"),
        )
    }
}