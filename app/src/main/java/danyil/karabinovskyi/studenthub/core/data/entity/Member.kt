package danyil.karabinovskyi.studenthub.core.data.entity

import java.util.Date

public data class Member(
    var user: User,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var isInvited: Boolean? = null,
    var banned: Boolean = false,
    var channelRole: String? = null,
)
