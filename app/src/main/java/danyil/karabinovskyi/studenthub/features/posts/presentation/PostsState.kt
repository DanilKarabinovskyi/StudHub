package danyil.karabinovskyi.studenthub.features.posts.presentation

data class PostsState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false,
)
