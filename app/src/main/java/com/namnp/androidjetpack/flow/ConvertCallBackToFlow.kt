package com.namnp.androidjetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConvertCallBackToFlow : ViewModel() {

    init {
        viewModelScope.launch {
            val downloadFlow = getDocument3()
                .shareIn( // convert cold Flow to hot Flow
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000), // if no subscribers after 5000, cancel Flow
                    replay = 1 // cache 1 latest value
                )
            downloadFlow.collect { snapshot ->
                // This is the snapshot placed on the conveyor belt earlier
            }
        }
    }


    suspend fun getDocument2() = suspendCoroutine<DocumentSnapshot> { continuation ->
        Firebase.firestore.collection("users").document("me")
            .get() // Start the asynchronous download of the "me" document
            .addOnSuccessListener { result ->
                // Resumes the suspended coroutine with the document we've received
                // return once, if emit multiple time -> use Flow (using callbackFlow)
                // if not, throw already resumed exception
                continuation.resume(result)
            }
    }

    private fun getDocument3(): Flow<DocumentSnapshot?> {
        // Create the flow "conveyor belt" using callbackFlow:
        // convert callback to a cold Flow (return multiple things from an async callback)
        // The code in the callbackFlow lambda runs as soon as a collector starts collecting
        // Each new collector will cause the code to run again, even if it’s running in parallel with another collector
        return callbackFlow {// Cold flow
            // Ask Firestore to keep us updated on changes to the "me" document
            val listener = Firebase.firestore.collection("users").document("me")
                .addSnapshotListener { snapshot, error -> // multiple time result
                    // This function will be called whenever the "me" document is updated.
                    // Load the updated document onto the "conveyor belt" of this flow:
                    trySend(snapshot) // don't need to be used in coroutine context
//                    send(snapshot) // must be used in coroutine context
                }
            // The awaitClose block runs whenever the collector stops collecting
            awaitClose {
                // Shut down the connection to Firestore
                listener.remove()
            }
        }
    }

    fun getDocument1() { // normal callback
        Firebase.firestore.collection("users").document("me")
            .get() // Start the asynchronous download of the "me" document
            .addOnSuccessListener { result ->
                // This function will be called the download completes
            }
    }
}