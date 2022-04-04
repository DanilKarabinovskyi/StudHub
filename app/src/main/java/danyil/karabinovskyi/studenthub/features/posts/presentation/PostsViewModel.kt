package danyil.karabinovskyi.studenthub.features.posts.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
//    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
}