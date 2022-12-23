package danyil.karabinovskyi.studenthub.features.posts.presentation.create_edit

import android.net.Uri

sealed class CreateEditPostEvent {
    data class EnterTitle(val value: String): CreateEditPostEvent()
    data class EnterDescription(val value: String): CreateEditPostEvent()
    data class PickImage(val uri: Uri?): CreateEditPostEvent()
    data class CropImage(val uri: Uri?): CreateEditPostEvent()
    object PostImage: CreateEditPostEvent()
}
