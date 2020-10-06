package kr.co.bakeapplication.data

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class RecipePage(
    var title: String,
    var description: String
): Serializable