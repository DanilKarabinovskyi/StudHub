package danyil.karabinovskyi.studenthub.components.text_fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textLabel: String,
    text: String = "",
    maxLength: Int = 400,
    error: String = "",
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    trailingIcon: @Composable() (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = {
            Text(text = textLabel)
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        )
    )
    if (error.isNotEmpty()) {
        Text(
            text = error,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}