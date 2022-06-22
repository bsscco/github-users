package com.example.githubusers.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.designsys.theme.GithubUsersTheme

@Composable
internal fun SearchBox(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
) {
    InputField(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        text = keyword,
        onTextChanged = { text -> onKeywordChanged(text) },
    )
}

@Composable
private fun InputField(
    modifier: Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = text,
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        onValueChange = { value -> onTextChanged(value) },
        placeholder = { Text("유저이름을 입력해주세요.") },
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    GithubUsersTheme {
        SearchBox(
            keyword = "",
            onKeywordChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledPreview() {
    GithubUsersTheme {
        SearchBox(
            keyword = "검색어",
            onKeywordChanged = {},
        )
    }
}