package danyil.karabinovskyi.studenthub.components.bottom_sheet

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(list: List<FormFilter>, state: ModalBottomSheetState, onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn {
        items(list.size){ index ->
            BottomSheetItem(
                formFilter = list[index],
                onItemClick = { title ->
                    onItemClick(title)
                    scope.launch {
                        state.hide()
                    }
                })
        }
    }
}