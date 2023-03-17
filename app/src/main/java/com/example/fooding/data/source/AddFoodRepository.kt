package com.example.fooding.data.source

import android.net.Uri
import android.util.Log
import com.example.fooding.data.model.Nutrition
import com.example.fooding.data.model.Post
import com.example.fooding.data.source.remote.PostApiClient
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class AddFoodRepository(
    private val postApiClient: PostApiClient
) {

    private val imgRef = Firebase.storage.reference.child("images")
    private val postRef = Firebase.database.reference.child("post")

    suspend fun uploadImg(uri: Uri): Uri {
        val uploadTask = imgRef.putFile(uri)

        return try {
            val downloadUri = uploadTask
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imgRef.downloadUrl
                }
                .await()

            downloadUri
        } catch (e: Exception) {
            Log.d("imgTask", "fail: $e")
            Uri.EMPTY
        }
    }

    suspend fun uploadPost(
        title: String?,
        date: Long?,
        time: String?,
        category: String?,
        memo: String?,
        imgUri: Uri?,
        nutrition: Nutrition?,
    ) {
        val downloadUri = if (imgUri != null) uploadImg(imgUri) else Uri.EMPTY
        postApiClient.uploadPost(Post(title, date, time, category, memo, downloadUri, nutrition))
    }
}