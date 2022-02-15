package danyil.karabinovskyi.studenthub.common.model

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)
