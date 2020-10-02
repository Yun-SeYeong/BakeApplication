package kr.co.bakeapplication.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recipe(
    var recipename: String,
    var creatoruid: String,
    var pages: ArrayList<RecipePage>?
) {
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "recipename" to recipename,
            "creatorname" to creatoruid,
            "pages" to pages
        )
    }

    @Exclude
    fun toObj(map: Map<String, Any?>) {
        recipename = map["recipename"].toString()
        creatoruid = map["creatorname"].toString()
        pages = map["pages"] as ArrayList<RecipePage>
    }
}