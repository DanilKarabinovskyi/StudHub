package danyil.karabinovskyi.studenthub.common.utils

private const val SYNC_NEEDED_STATUS_CODE = -1
private const val COMPLETED_STATUS_CODE = 1
private const val FAILED_PERMANENTLY_STATUS_CODE = 2
private const val IN_PROGRESS_STATUS_CODE = 3
private const val AWAITING_ATTACHMENTS_STATUS_CODE = 4


enum class SyncStatus(val status: Int) {

    SYNC_NEEDED(SYNC_NEEDED_STATUS_CODE),
    COMPLETED(COMPLETED_STATUS_CODE),
    FAILED_PERMANENTLY(FAILED_PERMANENTLY_STATUS_CODE),
    IN_PROGRESS(IN_PROGRESS_STATUS_CODE),
    AWAITING_ATTACHMENTS(AWAITING_ATTACHMENTS_STATUS_CODE);

    companion object {
        private val map = values().associateBy(SyncStatus::status)
        fun fromInt(type: Int): SyncStatus? = map[type]
    }
}
