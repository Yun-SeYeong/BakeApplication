package kr.co.bakeapplication.repositorys

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File

class FirebaseStorageRepository {
    private val _storage by lazy {
        Firebase.storage("gs://bakeapplication.appspot.com")
    }

    private val _storageRef = _storage.reference

    fun uploadFile(uploadPath: String, path: String): UploadTask {
        val uri = Uri.fromFile(File(path))
        val recipeImageRef = _storageRef.child("$uploadPath")
       return recipeImageRef.putFile(uri)
    }

    fun getDownloadUrl(path: String): Task<Uri> {
        return _storageRef.child(path).downloadUrl
    }

}