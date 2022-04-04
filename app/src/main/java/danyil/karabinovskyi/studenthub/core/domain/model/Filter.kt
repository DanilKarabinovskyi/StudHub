package danyil.karabinovskyi.studenthub.core.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
class Filter(
    val name: String,
    enabled: Boolean = false,
    val icon: ImageVector? = null
) {
    val enabled = mutableStateOf(enabled)
}
val filters = listOf(
    Filter(name = "Sport"),
    Filter(name = "Alchemical"),
    Filter(name = "Organic"),
    Filter(name = "Ride"),
    Filter(name = "Garden"),
    Filter(name = "Math"),
    Filter(name = "Celebrities"),
    Filter(name = "Fights"),
)
val priceFilters = listOf(
    Filter(name = "$"),
    Filter(name = "$$"),
    Filter(name = "$$$"),
    Filter(name = "$$$$")
)
val sortFilters = listOf(
    Filter(name = "Android's favorite (default)", icon = Icons.Filled.Android),
    Filter(name = "Rating", icon = Icons.Filled.Star),
    Filter(name = "Alphabetical", icon = Icons.Filled.SortByAlpha)
)

val categoryFilters = listOf(
    Filter(name = "Chips & crackers"),
    Filter(name = "Fruit snacks"),
    Filter(name = "Desserts"),
    Filter(name = "Nuts")
)
val lifeStyleFilters = listOf(
    Filter(name = "Organic"),
    Filter(name = "Gluten-free"),
    Filter(name = "Dairy-free"),
    Filter(name = "Sweet"),
    Filter(name = "Savory")
)
val postsTags = listOf(
    Filter(name = "Sport"),
    Filter(name = "Alchemical"),
    Filter(name = "Organic"),
    Filter(name = "Ride"),
    Filter(name = "Garden"),
    Filter(name = "Math"),
    Filter(name = "Celebrities"),
    Filter(name = "Fights"),
)
val selfTags = listOf(
    Filter(name = "All"),
    Filter(name = "Your University"),
    Filter(name = "Your"),
)