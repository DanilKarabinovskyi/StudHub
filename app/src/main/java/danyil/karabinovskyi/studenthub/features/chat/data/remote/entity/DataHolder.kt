package danyil.karabinovskyi.studenthub.features.chat.data.remote.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DataHolder(
    val id:Int,
    val name:String,
): Parcelable