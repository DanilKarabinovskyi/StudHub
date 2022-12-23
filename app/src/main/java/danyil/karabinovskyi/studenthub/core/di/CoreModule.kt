package danyil.karabinovskyi.studenthub.core.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.core.data.remote.CoreApi
import danyil.karabinovskyi.studenthub.core.data.repository.CoreRepositoryImpl
import danyil.karabinovskyi.studenthub.core.domain.CoreRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Provides
    @Singleton
    fun provideCoreApi(client: OkHttpClient): CoreApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(CoreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoreRepository(
        api: CoreApi,
        gson: Gson,
    ): CoreRepository {
        return CoreRepositoryImpl(api, gson)
    }

}