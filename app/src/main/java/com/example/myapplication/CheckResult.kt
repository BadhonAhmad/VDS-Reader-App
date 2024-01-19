package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckResult : AppCompatActivity() {
    // Base URL for the API
    private val baseUrl = "http://10.200.194.29:5001/"

    // Views
    private lateinit var regNoEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultcheck)

        // Initialize views
        regNoEditText = findViewById(R.id.reg_no)
        dateOfBirthEditText = findViewById(R.id.dateofbirth)
        submitButton = findViewById(R.id.submit_button)

        // Set click listener for the submit button
        submitButton.setOnClickListener {
            // Get user inputs
            val regNo = regNoEditText.text.toString()
            val dateOfBirth = dateOfBirthEditText.text.toString()

            // Validate inputs before making the API call
            if (regNo.isNotEmpty() && dateOfBirth.isNotEmpty()) {
                // Create Retrofit instance
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                // Create ApiService
                val apiService = retrofit.create(ApiService::class.java)

                // Create send-data object
                val sendData = SendData(regNo.toInt(), dateOfBirth)

                // Make the API call
                val call = apiService.sendinfo(sendData)

                call.enqueue(object : Callback<SendData> {
                    override fun onResponse(call: Call<SendData>, response: Response<SendData>) {
                        // Handle successful response
                        if (response.isSuccessful) {
                            // If the API call is successful, navigate to the next activity
                            val intent = Intent(this@CheckResult, MarkSPage1::class.java)
                            startActivity(intent)
                        } else {
                            // Handle unsuccessful response
                            Log.e("API Call", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<SendData>, t: Throwable) {
                        // Handle failure
                        Log.e("API Call", "Error: ${t.message}")
                    }
                })
            } else {
                // Show a message to the user indicating that both fields are required
                // You can customize this message based on your UI design
                Log.d("Validation", "Both fields are required")
            }
        }
    }
}
