package danyil.karabinovskyi.studenthub.core.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
class FormFilter(
    val name: String,
    enabled: Boolean = false,
    val icon: ImageVector? = null
) {
    val enabled = mutableStateOf(enabled)
}
val filters = listOf(
    FormFilter(name = "Sport"),
    FormFilter(name = "Alchemical"),
    FormFilter(name = "Organic"),
    FormFilter(name = "Ride"),
    FormFilter(name = "Garden"),
    FormFilter(name = "Math"),
    FormFilter(name = "Celebrities"),
    FormFilter(name = "Fights"),
)
val priceFilters = listOf(
    FormFilter(name = "$"),
    FormFilter(name = "$$"),
    FormFilter(name = "$$$"),
    FormFilter(name = "$$$$")
)
val sortFilters = listOf(
    FormFilter(name = SocialFilters.ALL.type, icon = Icons.Filled.Android),
    FormFilter(name = SocialFilters.MINE.type, icon = Icons.Filled.Star),
    FormFilter(name = SocialFilters.MY_UNIVERSITY.type, icon = Icons.Filled.SortByAlpha)
)

enum class SocialFilters(val type: String) {
    ALL("All"),
    MINE("Mine"),
    MY_UNIVERSITY("My University")
}

val categoryFilters = listOf(
    FormFilter(name = "Chips & crackers"),
    FormFilter(name = "Fruit snacks"),
    FormFilter(name = "Desserts"),
    FormFilter(name = "Nuts")
)
val lifeStyleFilters = listOf(
    FormFilter(name = "Organic"),
    FormFilter(name = "Gluten-free"),
    FormFilter(name = "Dairy-free"),
    FormFilter(name = "Sweet"),
    FormFilter(name = "Savory")
)
val postsTags = mutableListOf<FormFilter>(
    FormFilter(name = "Sport"),
    FormFilter(name = "Alchemical"),
    FormFilter(name = "Organic"),
    FormFilter(name = "Ride"),
    FormFilter(name = "Garden"),
    FormFilter(name = "Math"),
    FormFilter(name = "Celebrities"),
    FormFilter(name = "Fights"),
)
val selfTags = listOf(
    FormFilter(name = "All"),
    FormFilter(name = "Your University"),
    FormFilter(name = "Your"),
)