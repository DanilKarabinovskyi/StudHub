package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post


data class PostDetailState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false
)
