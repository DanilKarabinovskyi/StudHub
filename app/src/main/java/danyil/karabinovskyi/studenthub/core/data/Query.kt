package danyil.karabinovskyi.studenthub.core.data

import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.core.domain.model.SocialFilters

data class Query(
    val take: Int = Constants.DEFAULT_PAGE_SIZE,
    val skip: Int  = Constants.DEFAULT_SKIP_SIZE,
    val order: String = Constants.ASC,
    val sort: String = "",
    val filter: Filter = Filter(),
    val socialTag: String = SocialFilters.ALL.type,
    )
