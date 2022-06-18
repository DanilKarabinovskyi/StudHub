package danyil.karabinovskyi.studenthub.components.filter_bar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.components.surface.Surface
import danyil.karabinovskyi.studenthub.components.view.diagonalGradientBorder
import danyil.karabinovskyi.studenthub.components.view.fadeInDiagonalGradientBorder
import danyil.karabinovskyi.studenthub.components.view.offsetGradientBackground
import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun FilterBar(
    formFilters: List<FormFilter>,
    showFilterIcon: Boolean = false,
    onShowFilters: () -> Unit,
    onFilterClick: (String) -> Unit
) {

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
        modifier = Modifier.heightIn(min = 38.dp)
    ) {
        if (showFilterIcon){
            item {
                IconButton(onClick = onShowFilters) {
                    Icon(
                        imageVector = Icons.Rounded.FilterList,
                        tint = StudentHubTheme.colors.brand,
                        contentDescription = stringResource(danyil.karabinovskyi.studenthub.R.string.label_filters),
                        modifier = Modifier.diagonalGradientBorder(
                            colors = StudentHubTheme.colors.interactiveSecondary,
                            shape = CircleShape,
                        )
                    )
                }
            }
        }
        items(formFilters) { filter ->
            FilterChip(formFilter = filter, shape = MaterialTheme.shapes.small, onFilterClick = {onFilterClick(filter.name)})
        }
    }
}

@Composable
fun FilterChip(
    formFilter: FormFilter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    onFilterClick: (String) -> Unit
) {
    val (selected, setSelected) = formFilter.enabled
    val backgroundColor by animateColorAsState(
        if (selected) StudentHubTheme.colors.brandSecondary else StudentHubTheme.colors.uiBackground
    )
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = !selected,
        colors = StudentHubTheme.colors.interactiveSecondary,
        shape = shape
    )
    val textColor by animateColorAsState(
        if (selected) Color.Black else StudentHubTheme.colors.textSecondary
    )

    Surface(
        modifier = modifier.height(28.dp),
        color = backgroundColor,
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        val pressed by interactionSource.collectIsPressedAsState()
        val backgroundPressed =
            if (pressed) {
                Modifier.offsetGradientBackground(
                    StudentHubTheme.colors.interactiveSecondary,
                    200f,
                    0f
                )
            } else {
                Modifier.background(Color.Transparent)
            }
        Box(
            modifier = Modifier
                .toggleable(
                    value = selected,
                    onValueChange = {
                        onFilterClick(formFilter.name)
                        setSelected(it)
                    },
                    interactionSource = interactionSource,
                    indication = null,
                )
                .then(backgroundPressed)
                .then(border)
        ) {
            Text(
                text = formFilter.name,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
        }
    }
}