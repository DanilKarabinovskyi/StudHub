package danyil.karabinovskyi.studenthub.features.chat.presentation.chat

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import danyil.karabinovskyi.studenthub.common.contracts.CropActivityResultContract
import danyil.karabinovskyi.studenthub.common.extensions.observeLifecycle
import danyil.karabinovskyi.studenthub.components.buttons.JumpToBottom
import danyil.karabinovskyi.studenthub.components.buttons.JumpToUnreadMessages
import danyil.karabinovskyi.studenthub.components.messages.Message
import danyil.karabinovskyi.studenthub.components.text_fields.UserChatInput
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val messages = viewModel.messages
    viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
    val jumpToBottomButtonEnabled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex != 0
        }
    }
    val jumpToUnreadButtonEnabled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 && viewModel.lastReadPosition.value != 0
        }
    }

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(9f, 9f)
    ) {
        viewModel.onEvent(ChatViewModel.Companion.ChatViewModelEvent.PickImage(it))
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        it?.let { cropActivityLauncher.launch(it) }
    }

    LaunchedEffect(key1 = Unit){
        viewModel.eventFlow.collectLatest {
            when(it){
                is ChatScreenUiState.ScrollTo ->{
                    scrollState.scrollToItem(it.position)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolbar(
            title = {
                Text(
                    text = viewModel.chatName.value,
                    fontWeight = FontWeight.Bold,
                    color = StudentHubTheme.colorsV2.primary
                )
            },
            onNavigateUp = {
                onNavigateUp()
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(1f),
                        reverseLayout = true,
                        state = scrollState,
                        contentPadding = rememberInsetsPaddingValues(
                            insets = LocalWindowInsets.current.statusBars,
                            additionalTop = 30.dp
                        ),
                    ) {
                        for (i in 0 until messages.size) {
                            val prevAuthor = messages.getOrNull(i - 1)?.user
                            val nextAuthor = messages.getOrNull(i + 1)?.user
                            val content = messages[i]
                            val isFirstMessageByAuthor = prevAuthor != content.user
                            val isLastMessageByAuthor = nextAuthor != content.user
                            item {
                                Message(
                                    onAuthorClick = {
//                                    name -> navigateToProfile(name) todo
                                    },
                                    msg = content,
                                    isUserMe = viewModel.user.value.id == content.user.id,
                                    isFirstMessageByAuthor = isFirstMessageByAuthor,
                                    isLastMessageByAuthor = isLastMessageByAuthor
                                )
                            }
                        }

                    }
                    JumpToBottom(
                        // Only show if the scroller is not at the bottom
                        enabled = jumpToBottomButtonEnabled,
                        onClicked = {
                            scope.launch {
                                scrollState.animateScrollToItem(0)
                            }
                        },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                    JumpToUnreadMessages(
                        // Only show if the scroller is not at the bottom
                        enabled = jumpToUnreadButtonEnabled,
                        onClicked = {
                            scope.launch {
                                scrollState.animateScrollToItem(viewModel.lastReadPosition.value)
                                viewModel.lastReadPosition.value = 0
                            }
                        },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                UserChatInput(
                    onMessageSent = { content ->
                        viewModel.sendMessage(content)
                    },
                    resetScroll = {
                    },
                    // Use navigationBarsWithImePadding(), to move the input panel above both the
                    // navigation bar, and on-screen keyboard (IME)
                    modifier = Modifier.navigationBarsWithImePadding(),
                    imageUri = viewModel.messageImageUri,
                    onPictureClick = {
                        galleryLauncher.launch("image/*")
                    }
                )

            }
        }
    }
}
 sealed class ChatScreenUiState{
     data class ScrollTo(val position:Int): ChatScreenUiState()
 }

data class ChatScreenState(
    val messages: MutableList<Message> = mutableListOf<Message>(),
    val isLoading: Boolean = false
)