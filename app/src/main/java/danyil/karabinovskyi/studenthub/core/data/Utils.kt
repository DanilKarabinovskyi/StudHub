package danyil.karabinovskyi.studenthub.core.data

import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiText
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleErrors(work: suspend () -> Resource<T>): Resource<T> {
    return try {
        work.invoke()
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
// todo improve
//suspend fun <T> handleResponse(response: BasicApiResponse<T>, work: suspend () -> Resource<T>): Resource<T> {
//    return if (response.successful) {
//        work.invoke()
//    } else {
//        response.message?.let { msg: String ->
//            Resource.Error<T>(UiText.DynamicString(msg))
//        } ?: Resource.Error<T>(UiText.StringResource(R.string.error_unknown))
//    }
//}

