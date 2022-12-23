package danyil.karabinovskyi.studenthub.core.data.remote

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.core.data.entity.AppData
import retrofit2.http.GET

interface CoreApi {

    @GET("global/init-app-data")
    suspend fun initAppData(): BasicApiResponse<AppData>
}