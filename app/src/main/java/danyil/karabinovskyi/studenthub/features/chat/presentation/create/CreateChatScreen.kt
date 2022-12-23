package danyil.karabinovskyi.studenthub.features.chat.presentation.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.contracts.CropActivityResultContract
import danyil.karabinovskyi.studenthub.common.model.AuthError
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.components.buttons.RoundedButton
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.components.user.UserItem
import danyil.karabinovskyi.studenthub.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateChatScreen(
    viewModel: CreateChatViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
    imageLoader: ImageLoader,
) {
    val pagingState = viewModel.pagingState.value
    val focusManager = LocalFocusManager.current
    val cropChatImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(CreateChatEvent.AddChatImage(it))
    }
    val cropChatGalleryImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if(it == null) {
            return@rememberLauncherForActivityResult
        }
        cropChatImageLauncher.launch(it)
    }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {
            when(it){
                UiEvent.NavigateUp ->{
                    onNavigateUp()
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = StudentHubTheme.colorsV2.appBackground)
    ) {
        StandardToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.create_chat),
                    fontWeight = FontWeight.Bold,
                    color = StudentHubTheme.colors.textPrimary
                )
            },
            onNavigateUp = onNavigateUp,
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = SpaceMedium)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SpaceSmall),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = StudentHubTheme.colors.iconPrimary
                )
                Image(painter =
                        rememberImagePainter(
                            data = viewModel.pictureUri.value, imageLoader = imageLoader,
                        )
                    ,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(ProfilePictureSizeLarge)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = StudentHubTheme.colors.iconPrimary,
                            shape = CircleShape
                        )
                        .clickable {
                            cropChatGalleryImageLauncher.launch("image/*")
                        }
                )
            }
            TransparentTextField(
                text = viewModel.channelNameState.value.text,
                onValueChange = {
                    viewModel.onEvent(CreateChatEvent.OnEnterChatName(it))
                },
                textLabel = stringResource(id = R.string.name_of_the_chat),
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                backgroundColor = StudentHubTheme.colorsV2.inputBackground,
                error = when (viewModel.channelNameState.value.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                }
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = SpaceMedium)) {
                LazyColumn(modifier = Modifier.padding(bottom = SpaceXXLarge)) {
                    items(pagingState.items.size) { i ->
                        val user = pagingState.items[i]
                        if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                            viewModel.loadNextUsers()
                        }
                        UserItem(
                            user = user,
                            onItemSelected = { id ->
                                viewModel.addOrDeleteId(id)
                            }
                        )
                        if (i < pagingState.items.size - 1) {
                            Spacer(modifier = Modifier.height(SpaceSmall))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
                if (pagingState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                RoundedButton(modifier = Modifier.align(Alignment.BottomCenter),
                    enabled = !viewModel.loading.value,
                    text = stringResource(id = R.string.create_chat),
                    displayProgressBar = viewModel.loading.value) {
                    viewModel.onEvent(CreateChatEvent.CreateChat)
                }
            }
        }
    }
}