package com.example.herbario_nacional.data.network.services

import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}