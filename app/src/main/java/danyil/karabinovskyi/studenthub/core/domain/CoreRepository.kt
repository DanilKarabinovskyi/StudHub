package danyil.karabinovskyi.studenthub.core.domain

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.entity.AppData

interface CoreRepository {
    suspend fun initAppData(): Resource<AppData>
}