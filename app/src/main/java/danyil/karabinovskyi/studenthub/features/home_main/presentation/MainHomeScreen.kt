package danyil.karabinovskyi.studenthub.features.home_main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.components.post.PostListItem
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun MainHomeScreen(
    viewModel: MainHomeViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        StandardToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    color = StudentHubTheme.colorsV2.primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {}
        )
        if (viewModel.posts.value != emptyList<Post>()) {
            PostsSection(viewModel)
        }
    }
}

@Composable
fun PostsSection(viewModel: MainHomeViewModel) {
    Column(
        modifier = Modifier
            .padding(StudentHubTheme.dimensions.SpaceSmall)
            .background(StudentHubTheme.colorsV2.overlay)
            .clip(RoundedCornerShape(4.dp))
    ) {
        StudText(
            text = stringResource(id = R.string.posts_you_may_like),
            style = StudentHubTheme.typography.bodyLarge,
            color = StudentHubTheme.colorsV2.primary,
            fontSize = StudentHubTheme.dimensions.SizeXXLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = StudentHubTheme.dimensions.SpaceSmall,
                start = StudentHubTheme.dimensions.SpaceSmall,
                top = StudentHubTheme.dimensions.SpaceSmall
            )
        )
        LazyColumn(
            modifier = Modifier
                .height(338.dp)
        ) {
            items(viewModel.posts.value.size) { i ->
                val post = viewModel.posts.value[i]
                PostListItem(
                    item = post,
                    onClick = {

                    }
                )
            }
        }
    }

}