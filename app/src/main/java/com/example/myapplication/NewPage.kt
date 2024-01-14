package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class NewPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_page)

        val result = intent.getStringExtra("QR_RESULT")

        val textView: TextView = findViewById(R.id.textView)
        result?.let {
            textView.text = it
        }

        // Alternatively, you can use the apply function for brevity
        // result?.apply { textView.text = this }
    }
}
