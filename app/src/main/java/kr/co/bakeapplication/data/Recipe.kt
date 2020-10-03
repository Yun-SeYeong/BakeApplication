package kr.co.bakeapplication.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Recipe(
    var recipename: String,
    var creatoruid: String,
    var pages: ArrayList<RecipePage>?
): Serializable{
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
        pages = ArrayList<RecipePage>()
        for (page in (map["pages"] as ArrayList<HashMap<String, String>>)){
            pages!!.add(RecipePage(page["title"]!!, page["description"]!!))
        }
    }
}