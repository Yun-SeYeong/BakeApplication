package kr.co.bakeapplication.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Profile(
    var firebasetoken: String?,
    var profilename: String?,
    var profileImg: String?
): Serializable{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "firebasetoken" to firebasetoken,
            "profilename" to profilename,
            "profileImg" to profileImg
        )
    }

    @Exclude
    fun toObj(map: Map<String, Any?>) {
        firebasetoken = map["firebasetoken"].toString()
        profilename = map["profilename"].toString()
        profileImg = map["profileImg"].toString()
    }
}