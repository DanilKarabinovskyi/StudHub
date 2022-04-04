package danyil.karabinovskyi.studenthub.components.bottom_sheet

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import danyil.karabinovskyi.studenthub.core.domain.model.Filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(list: List<Filter>, state: ModalBottomSheetState, onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn {
        items(list.size){ index ->
            BottomSheetItem(
                filter = list[index],
                onItemClick = { title ->
                    onItemClick(title)
                    scope.launch {
                        state.hide()
                    }
                })
        }
    }
}