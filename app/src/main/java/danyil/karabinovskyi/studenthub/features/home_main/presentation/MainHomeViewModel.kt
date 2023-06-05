package danyil.karabinovskyi.studenthub.features.home_main.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Event
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainHomeViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val postUseCases: PostUseCases,
) : ViewModel(), DefaultLifecycleObserver {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val result = postUseCases.getPostsUseCase.execute(
                query = Query().copy(
                    take = 3,
                )
            )
            when (result) {
                is Resource.Success -> {
                    _posts.value = result.data!!
                }
                is Resource.Error -> {
                }
            }
        }
    }
}