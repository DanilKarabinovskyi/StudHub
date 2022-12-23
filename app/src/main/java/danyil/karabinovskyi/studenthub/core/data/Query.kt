package danyil.karabinovskyi.studenthub.core.data

import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.core.domain.model.SocialFilters

data class Query(
    val take: Int = Constants.DEFAULT_PAGE_SIZE,
    val skip: Int = Constants.DEFAULT_SKIP_SIZE,
    val order: String = Constants.ASC,
    val sort: String = "",
    var filter: Filter = Filter(),
    var socialTag: String = SocialFilters.ALL.type,
)

data class ChatQuery(
    val take: Int = Constants.DEFAULT_PAGE_SIZE,
    val skip: Int = Constants.DEFAULT_SKIP_SIZE,
    val order: String = Constants.ASC,
    val sort: String = "id",
    var search: String = "",
)
