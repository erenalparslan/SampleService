package com.erenalparslan.services

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
   inner class MyResultReceiver(handler: Handler?) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == 1 && resultData != null) {
                handler.post {
                    val result = resultData.getString("websiteResult")
                    textView.setText(result)
                }
            }
        }

    }

    fun download(view: View?) {
        val myResultReceiver: ResultReceiver = MyResultReceiver(null)
        val intent = Intent(this, DownloadClass::class.java)
        val userInput = editText.text.toString()
        intent.putExtra("url", userInput)
        textView.text = "downloading..."
        intent.putExtra("receiver", myResultReceiver)
        startService(intent)
    }

}

