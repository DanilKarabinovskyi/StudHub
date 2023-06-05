package danyil.karabinovskyi.studenthub.components.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun PostListItem(
    item: Post,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        Divider(
            color = StudentHubTheme.colorsV2.disabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Row(
            modifier = Modifier
                .height(StudentHubTheme.dimensions.ItemSizeXXLarge)
                .fillMaxWidth()
        ) {
            Divider(
                color = StudentHubTheme.colorsV2.disabled,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            AsyncImage(
                modifier = Modifier.size(
                    width = StudentHubTheme.dimensions.ItemSizeXXXLarge,
                    height = StudentHubTheme.dimensions.ItemSizeXXLarge
                ),
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = StudentHubTheme.dimensions.SpaceSmall,
                        start = StudentHubTheme.dimensions.SpaceMedium,
                        bottom = StudentHubTheme.dimensions.SpaceSmall,
                        end = StudentHubTheme.dimensions.SpaceLarge
                    )
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                StudText(
                    modifier = Modifier
                        .padding(
                            end = StudentHubTheme.dimensions.SpaceSSmall,
                        ),
                    text = item.title, maxLines = 2, style = StudentHubTheme.typography.bodyBold
                )
                StudText(
                    text = item.description,
                    style = StudentHubTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.weight(1f))
//                ArticleInfo(item, newsTypeIcon)
            }
            Divider(
                color = StudentHubTheme.colorsV2.disabled,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.007f)
            )
        }
    }

}

//@Composable
//fun ArticleInfo(item: Post, @DrawableRes newsTypeIcon: Int) {
//    Row(
//        modifier = Modifier.padding(top = StudentHubTheme.dimensions.SpaceSMedium),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(id = newsTypeIcon), contentDescription = null
//        )
//        Spacer(modifier = Modifier.width(StudentHubTheme.dimensions.SpaceSSmall))
//        CategoryText(item.category?.name ?: ArticleV2.defaultCategory.name)
//        Spacer(modifier = Modifier.weight(1f))
//        StudText(
//            text = DateHelper.getMonthDayYearString(DateHelper.dateFromString(item.datePosted)),
//            style = StudentHubTheme.typography.body3.copy(color = StudentHubTheme.colors.eclipse),
//        )
//    }
//}

//@Composable
//fun CategoryText(text: String) {
//    Box(
//        modifier = Modifier
//            .background(
//                RapNetTheme.colors.coldGray,
//                shape = RapNetTheme.shapes.RoundedCornerShapeSSmall
//            )
//            .border(
//                1.dp,
//                RapNetTheme.colors.coldGray,
//                shape = RapNetTheme.shapes.RoundedCornerShapeSSmall
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        RapText(
//            modifier = Modifier
//                .padding(
//                    vertical = RapNetTheme.dimensions.SpaceSSSSmall,
//                    horizontal = RapNetTheme.dimensions.SpaceSmall
//                ),
//            text = text,
//            color = RapNetTheme.colors.black,
//            style = RapNetTheme.typography.body3,
//        )
//    }
//}