package danyil.karabinovskyi.studenthub.core.data.entity

import danyil.karabinovskyi.studenthub.common.utils.SyncStatus
import java.util.*

data class Message(
    var id: Int = 0,
    var message: String = "",
    var type: String = "",
    var image:String? = null,
    var isMessageRead: Boolean? = null,
    var syncStatus: SyncStatus? = null,
    var createdAt: String = "",
    var updatedAt: Date? = null,
    var deletedAt: String = "",
    var user: User = User(),
)
