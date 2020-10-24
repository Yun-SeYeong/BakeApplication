package kr.co.bakeapplication.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Recipe(
    var recipename: String,
    var creatoruid: String,
    var thumbNailUri: String,
    var pages: ArrayList<RecipePage>?
): Serializable{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "recipename" to recipename,
            "creatoruid" to creatoruid,
            "thumbNailUri" to thumbNailUri,
            "pages" to pages
        )
    }

    @Exclude
    fun toObj(map: Map<String, Any?>) {
        recipename = map["recipename"].toString()
        creatoruid = map["creatoruid"].toString()
        thumbNailUri = map["thumbNailUri"].toString()
        pages = ArrayList<RecipePage>()
        if (map["pages"] != null){
            for (page in (map["pages"] as ArrayList<HashMap<String, String>>)){
                pages!!.add(RecipePage(page["title"]!!, page["imageUri"]!!, page["description"]!!))
            }
        }

    }
}