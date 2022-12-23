package danyil.karabinovskyi.studenthub.core.data.repository

import com.google.gson.Gson
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.remote.CoreApi
import danyil.karabinovskyi.studenthub.core.data.entity.AppData
import danyil.karabinovskyi.studenthub.core.domain.CoreRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val api: CoreApi,
    private val gson: Gson
) : CoreRepository {
    override suspend fun initAppData(): Resource<AppData> {
        return try {
            val response = api.initAppData()
            if (response.successful) {
                if (response.data != null) {
                    Resource.Success(data = response.data)
                } else {
                    Resource.Error(uiText = UiText.StringResource(R.string.oops_something_went_wrong))
                }
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}