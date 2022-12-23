package danyil.karabinovskyi.studenthub.core.domain.usecase

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.entity.AppData
import danyil.karabinovskyi.studenthub.core.domain.CoreRepository
import javax.inject.Inject

class GetAppDataUseCase @Inject constructor(private val repository: CoreRepository) {

    suspend fun execute(): Resource<AppData> {
        val result = repository.initAppData()
        return result
    }
}