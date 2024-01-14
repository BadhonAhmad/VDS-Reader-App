package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R.layout.app_home

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(app_home)

        val button = findViewById<Button>(R.id.button1)
        val button1 = findViewById<Button>(R.id.button)
        button?.setOnClickListener {
            val intent1 = Intent(this, Qrscann::class.java)
            startActivity(intent1)
        }
        button1?.setOnClickListener {
            val intent2 = Intent(this,CheckResult::class.java)
            startActivity(intent2)
        }
    }
}
