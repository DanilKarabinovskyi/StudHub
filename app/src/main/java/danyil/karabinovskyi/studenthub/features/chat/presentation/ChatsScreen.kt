package danyil.karabinovskyi.studenthub.features.chat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.extensions.observeLifecycle
import danyil.karabinovskyi.studenthub.components.chat.channels.ChannelItem
import danyil.karabinovskyi.studenthub.components.text_fields.InputSelector
import danyil.karabinovskyi.studenthub.components.text_fields.UserInputText
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.components.view.StudDivider
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.core.data.entity.Channel
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
) {
    val chats = viewModel.filteredChats.value
    var searchIsShown by remember { mutableStateOf(true) }
    viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    var textFieldFocusState by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolbar(
            title = {
                if (searchIsShown) {
                    Text(
                        text = stringResource(id = R.string.all_chats),
                        fontWeight = FontWeight.Bold,
                        color = StudentHubTheme.colorsV2.primary
                    )
                } else {
                    UserInputText(
                        modifier = Modifier.focusRequester(focusRequester),
                        textFieldValue = viewModel.searchState.value.text,
                        onTextChanged = {
                            viewModel.onEvent(
                                ChatsViewModel.Companion.ChatsViewModelEvent.EnterSearchField(
                                    it
                                )
                            )
                        },
                        hint = "Enter chat name",
                        onTextFieldFocused = { focused ->
                            if (focused) {
                                currentInputSelector = InputSelector.NONE
                            }

                            textFieldFocusState = focused
                        },
                    )
                    LaunchedEffect(key1 = Unit) {
                        focusRequester.requestFocus()
                    }

                }

            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    if (!searchIsShown) {
                        viewModel.clearSearch()
                    }
                    searchIsShown = !searchIsShown
                }) {
                    if (!searchIsShown) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "",
                            tint = StudentHubTheme.colorsV2.iconPrimary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = StudentHubTheme.colorsV2.iconPrimary
                        )
                    }
                }
                IconButton(onClick = {
                    onNavigate(MainDestinations.CHAT_CREATE_EDIT + "/${-777}")
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = StudentHubTheme.colorsV2.iconPrimary
                    )
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(chats.chats.size) { i ->
                    ChannelItem(
                        channel = chats.chats[i],
                        currentUser = User(),
                        onChannelClick = {
                            onNavigate(MainDestinations.CHAT_ROUTE + "/${chats.chats[i].id}?chatName=${chats.chats[i].name}")
                        },
                        onChannelLongClick = {}
                    )
                    StudDivider()
                }
                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }
    }
}

data class ChatsScreenState(
    val chats: List<Channel> = emptyList(),
    val isLoading: Boolean = false
)