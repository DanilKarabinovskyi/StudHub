package danyil.karabinovskyi.studenthub.common.utils

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiText

interface Paginate<T> {

    suspend fun loadNextItems()

    suspend fun clear()

    class Base<T>(
        private val onLoadUpdated: (Boolean) -> Unit,
        private val onRequest: suspend (nextPage: Int) -> Resource<List<T>>,
        private val onError: suspend (UiText) -> Unit,
        private val onSuccess: (items: List<T>) -> Unit
    ) : Paginate<T> {

        private var page = 1

        override suspend fun loadNextItems() {
            onLoadUpdated(true)
            when (val result = onRequest(page)) {
                is Resource.Success -> {
                    val items = result.data ?: emptyList()
                    page++
                    onSuccess(items)
                    onLoadUpdated(false)
                }
                is Resource.Error -> {
                    onError(result.uiText ?: UiText.unknownError())
                    onLoadUpdated(false)
                }
            }
        }

        override suspend fun clear() {
            page = 1
        }
    }
}