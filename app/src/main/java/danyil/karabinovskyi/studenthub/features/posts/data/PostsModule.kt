package danyil.karabinovskyi.studenthub.features.posts.data

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.di.NetworkModule
import danyil.karabinovskyi.studenthub.features.auth.data.remote.api.AuthApi
import danyil.karabinovskyi.studenthub.features.auth.data.repository.AuthRepositoryImpl
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.posts.data.remote.api.PostsApi
import danyil.karabinovskyi.studenthub.features.posts.data.repository.PostsRepositoryImpl
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.*
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.LikePost
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class PostsModule {
    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostsApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(PostsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostsApi,
        gson: Gson,
    ): PostsRepository {
        return PostsRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideLikePostExecutor(): LikePost {
        return LikePost.Base()
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostsRepository): PostUseCases {
        return PostUseCases(
            createPostUseCase = CreatePostUseCase(repository),
            getPostsUseCase = GetPostsUseCase(repository),
            getPostUseCase = GetPostUseCase(repository),
            createCommentUseCase = CreateCommentUseCase(repository),
            getCommentsForPostUseCase = GetCommentsForPostUseCase(repository),
            deletePostUseCase = DeletePostUseCase(repository),
            likeToggleUseCase = LikeToggleUseCase(repository),
        )
    }
}