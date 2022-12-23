package danyil.karabinovskyi.studenthub.features.posts.presentation.util

sealed interface Like {
    object Post : Like
    object Comment : Like
}