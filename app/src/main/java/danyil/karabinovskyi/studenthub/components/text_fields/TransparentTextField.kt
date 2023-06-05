package danyil.karabinovskyi.studenthub.components.text_fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textLabel: String,
    text: String = "",
    hint: String = "",
    maxLength: Int = 400,
    error: String = "",
    backgroundColor: Color = Color.Transparent,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions = KeyboardActions(onNext = { }),
    imeAction: ImeAction = ImeAction.Next,
    maxLines: Int = 1,
    trailingIcon: @Composable() (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusRequester: FocusRequester = FocusRequester()
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = focusRequester),
        maxLines = maxLines,
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = {
            StudText(text = textLabel, color = StudentHubTheme.colorsV2.primary)
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        placeholder = {
            StudText(
                text = hint,
                color = StudentHubTheme.colorsV2.primary
            )
        },
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            focusedLabelColor = StudentHubTheme.colorsV2.primary,
            placeholderColor = StudentHubTheme.colorsV2.primary,
            cursorColor = StudentHubTheme.colorsV2.primaryAccent,
            unfocusedIndicatorColor = StudentHubTheme.colorsV2.primary,
            focusedIndicatorColor = StudentHubTheme.colorsV2.primaryAccent,
        ),
        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
    )
    if (error.isNotEmpty()) {
        Text(
            text = error,
            style = StudentHubTheme.typography.bodySmall,
            color = StudentHubTheme.colorsV2.errorAccent,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}