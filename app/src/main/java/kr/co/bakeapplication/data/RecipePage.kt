package kr.co.bakeapplication.data

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class RecipePage(
    var title: String,
    var imageUri: String,
    var description: String
): Serializable