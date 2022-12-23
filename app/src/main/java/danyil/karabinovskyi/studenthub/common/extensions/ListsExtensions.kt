package danyil.karabinovskyi.studenthub.common.extensions

import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter

fun List<String>.createFormValues():List<FormFilter>{
    val resultList = mutableListOf<FormFilter>()
    this.forEach {
        resultList.add(FormFilter(name = it))
    }
    return resultList
}