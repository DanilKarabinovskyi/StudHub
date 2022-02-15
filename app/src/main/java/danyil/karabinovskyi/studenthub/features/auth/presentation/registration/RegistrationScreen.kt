package danyil.karabinovskyi.studenthub.features.auth.presentation.registration

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.components.buttons.RoundedButton
import danyil.karabinovskyi.studenthub.components.buttons.SocialMediaButton
import danyil.karabinovskyi.studenthub.components.dialogs.EventDialog
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField
import danyil.karabinovskyi.studenthub.features.auth.presentation.document_scan.AppScanActivity.Companion.APP_SCAN_ACTIVITY_ERROR
import danyil.karabinovskyi.studenthub.features.auth.presentation.document_scan.DocumentScanResultContract
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import java.io.File

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onRegister: (String, String, File?, String, String) -> Unit,
    onBack: () -> Unit,
    onDismissDialog: () -> Unit
) {
    val photoValue = remember { mutableStateOf<File?>(null) }
    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val documentPath = remember { mutableStateOf<String?>(null) }
    val composableScope = rememberCoroutineScope()

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(DocumentScanResultContract()) {
        documentPath.value = it
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onBack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }

                Text(
                    text = "Create An Account",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransparentTextField(
                    textFieldValue = nameValue,
                    textLabel = "Group",
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = emailValue,
                    textLabel = "Email",
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = passwordValue,
                    textLabel = "Password",
                    keyboardType = KeyboardType.Password,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisibility = !passwordVisibility
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password Icon"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )

                TransparentTextField(
                    textFieldValue = confirmPasswordValue,
                    textLabel = "Confirm Password",
                    keyboardType = KeyboardType.Password,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()

                            onRegister(
                                nameValue.value,
                                emailValue.value,
                                photoValue.value,
                                passwordValue.value,
                                confirmPasswordValue.value
                            )
                        }
                    ),
                    imeAction = ImeAction.Done,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisibility = !confirmPasswordVisibility
                            }
                        ) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password Icon"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                documentPath.value?.let { path ->
                    if (path != APP_SCAN_ACTIVITY_ERROR) {
                        Image(
                            BitmapFactory.decodeFile(path).asImageBitmap(),
                            null,
                            modifier = Modifier.height(200.dp)
                        )
                        photoValue.value = File(path)
                    }

                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SocialMediaButton(
                        text = "Documents Photo",
                        onClick = {
                            launcher.launch(0)
                        },
                        socialMediaColor = StudentHubTheme.colors.textPrimary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                RoundedButton(
                    text = "Sign Up",
                    displayProgressBar = state.displayProgressBar,
                    onClick = {
                        onRegister(
                            nameValue.value,
                            emailValue.value,
                            photoValue.value,
                            passwordValue.value,
                            confirmPasswordValue.value
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append("Already have an account?")

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Log in")
                        }
                    },
                    onClick = {
                        onBack()
                    }
                )
            }
        }

        if (state.errorMessage != null) {
            EventDialog(errorMessage = state.errorMessage, onDismiss = onDismissDialog)
        }
    }
}