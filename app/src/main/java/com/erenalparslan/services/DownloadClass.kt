package com.erenalparslan.services

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.annotation.Nullable
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DownloadClass : IntentService("DownloadClass") {
    override fun onHandleIntent(@Nullable intent: Intent?) {
        val currentThread = Thread.currentThread().name
        Log.d(TAG, "onHandleIntent: $currentThread")
        val urlInput = intent!!.getStringExtra("url")
        val resultReceiver = intent.getParcelableExtra<ResultReceiver>("receiver")
        var result: String? = ""
        val url: URL
        var httpURLConnection: HttpURLConnection? = null
        try {
            url = URL(urlInput)
            httpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream = httpURLConnection!!.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            var data = inputStreamReader.read()
            while (data != -1) {
                val current = data.toChar()
                result += current
                data = inputStreamReader.read()
            }
            val bundle = Bundle()
            bundle.putString("websiteResult", result)
            resultReceiver!!.send(1, bundle)
        } catch (e: Exception) {
            val bundle = Bundle()
            bundle.putString("websiteResult", "Error!!")
            resultReceiver!!.send(1, bundle)
        }
    }

    override fun onCreate() {
        val currentThread = Thread.currentThread().name
        Log.d(TAG, "onCreate: $currentThread")
        super.onCreate()
    }

    override fun onDestroy() {
        val currentThread = Thread.currentThread().name
        Log.d(TAG, "onDestroy: $currentThread")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "DownloadClass"
    }
}