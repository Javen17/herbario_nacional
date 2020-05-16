package com.example.herbario_nacional.data.network.services

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService


class FirebaseMessagingService: FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val intent = Intent("TokenFMC")
        intent.putExtra("NewTokenFCM", token)
        broadcaster?.sendBroadcast(intent)
    }
}