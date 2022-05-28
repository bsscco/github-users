package com.example.githubusers.usersearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.designsys.theme.GithubUsersTheme

@Composable
internal fun SearchBox(
    keyword: String,
    onSearchIconClicked: (String) -> Unit,
    onDoneKeyClicked: (String) -> Unit,
) {
    var input by remember(keyword) { mutableStateOf(keyword) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
    ) {
        InputField(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            text = input,
            onTextChanged = { text -> input = text },
            onDoneKeyClicked = {
                focusManager.clearFocus(force = true)
                onDoneKeyClicked(input)
            }
        )

        SearchIcon(
            onClicked = {
                focusManager.clearFocus(force = true)
                onSearchIconClicked(input)
            },
        )
    }
}

@Composable
private fun InputField(
    modifier: Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    onDoneKeyClicked: () -> Unit,
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
        keyboardActions = KeyboardActions(onDone = { onDoneKeyClicked() }),
    )
}

@Composable
private fun SearchIcon(onClicked: () -> Unit) {
    Image(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClicked,
            )
            .padding(horizontal = 8.dp)
            .size(ButtonDefaults.IconSize * 2),
        imageVector = Icons.Filled.Search,
        contentDescription = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    GithubUsersTheme {
        SearchBox(
            keyword = "",
            onSearchIconClicked = {},
            onDoneKeyClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledPreview() {
    GithubUsersTheme {
        SearchBox(
            keyword = "검색어",
            onSearchIconClicked = {},
            onDoneKeyClicked = {},
        )
    }
}