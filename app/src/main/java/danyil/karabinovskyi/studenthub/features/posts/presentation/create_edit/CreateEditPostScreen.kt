package danyil.karabinovskyi.studenthub.features.posts.presentation.create_edit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.contracts.CropActivityResultContract
import danyil.karabinovskyi.studenthub.common.extensions.doesFileExistInAttachments
import danyil.karabinovskyi.studenthub.common.extensions.getFileName
import danyil.karabinovskyi.studenthub.common.extensions.saveFileFromUriToAttachments
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.asString
import danyil.karabinovskyi.studenthub.components.filter_bar.FilterBar
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.PostConstants
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.PostError
import danyil.karabinovskyi.studenthub.ui.theme.SpaceLarge
import danyil.karabinovskyi.studenthub.ui.theme.SpaceMedium
import danyil.karabinovskyi.studenthub.ui.theme.SpaceSmall
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@ExperimentalCoilApi
@Composable
fun CreateEditPostScreen(
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: CreateEditPostViewModel = hiltViewModel()
) {
    val imageUri = viewModel.chosenImageUri.value
    val context = LocalContext.current

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f, 9f)
    ) {
        viewModel.onEvent(CreateEditPostEvent.CropImage(it))
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        it?.let { cropActivityLauncher.launch(it) }
    }
    val attachmentsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if(uri != null){
            if(!context.doesFileExistInAttachments(context.getFileName(uri))){
                context.saveFileFromUriToAttachments(uri,context.getFileName(uri))
            }
            viewModel.onEvent(CreateEditPostEvent.AddAttachments(context.getFileName(uri)))
        }

    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    GlobalScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.uiText.asString(context)
                        )
                    }
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.post),
                    fontWeight = FontWeight.Bold,
                    color = StudentHubTheme.colorsV2.primary
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground
                )
                viewModel.oldImageUrl.value?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = url,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(
                            data = uri,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            FilterBar(
                viewModel.formFilters,
                showFilterIcon = false,
                onFilterClick = { filters ->
                    viewModel.setChosenFilters(filters)
                })
            Spacer(modifier = Modifier.height(SpaceMedium))
            TransparentTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.titleState.value.text,
                hint = stringResource(id = R.string.enter_a_title),
                error = when (viewModel.titleState.value.error) {
                    is PostError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                maxLines = 2,
                maxLength = PostConstants.MAX_POST_TITLE_LENGTH,
                onValueChange = {
                    viewModel.onEvent(
                        CreateEditPostEvent.EnterTitle(it)
                    )
                },
                textLabel = stringResource(id = R.string.title),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next

            )
            TransparentTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                hint = stringResource(id = R.string.enter_a_text),
                error = when (viewModel.descriptionState.value.error) {
                    is PostError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                maxLines = 125,
                maxLength = PostConstants.MAX_POST_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(
                        CreateEditPostEvent.EnterDescription(it)
                    )
                },
                textLabel = stringResource(id = R.string.description),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next

            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(
                shape = StudentHubTheme.shapes.quotedAttachment,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = StudentHubTheme.colorsV2.buttonBackgroundPrimary
                ),
                onClick = {
                    attachmentsLauncher.launch("*/*")
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier
                    .align(Alignment.Start),
            ) {
                Text(
                    text = stringResource(id = R.string.attach_files),
                    color = StudentHubTheme.colorsV2.primary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = StudentHubTheme.colorsV2.primaryAccent,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = StudentHubTheme.colorsV2.iconPrimary
                    )
                }
            }
            LazyRow {
                items(viewModel.attachmentsUri.value.size) { i ->
                    val attachment = viewModel.attachmentsUri.value[i]
                    Column(modifier = Modifier.width(100.dp)) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        StudText(text = attachment)
                    }
                }
            }
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(
                shape = StudentHubTheme.shapes.quotedAttachment,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = StudentHubTheme.colorsV2.buttonBackgroundPrimary
                ),
                onClick = {
                    viewModel.onEvent(CreateEditPostEvent.CreatePost)
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier
                    .align(Alignment.End),
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = StudentHubTheme.colorsV2.primary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = StudentHubTheme.colorsV2.primaryAccent,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = StudentHubTheme.colorsV2.iconPrimary
                    )
                }
            }
        }
    }
}