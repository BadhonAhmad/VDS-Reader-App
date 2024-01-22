package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckResult : AppCompatActivity() {
    // Base URL for the API
    private val baseUrl = "http://10.200.192.126:5001/"

    // Views
    private lateinit var regNoEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var submitButton: Button

    object DataManager {
        var studentInfo: StudentInfo? = null
    }

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
                try {
                    val regNoInt = regNo.toInt()

                    // Create Retrofit instance
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    // If the API call is successful, navigate to the next activity
                    val apiService = retrofit.create(ApiService::class.java)
                    val sendData = SendData(regNo.toInt(), dateOfBirth)

                    val getStudentInfoCall: Call<List<StudentInfo>> =
                        apiService.getStudentInfo(sendData)

                    getStudentInfoCall.enqueue(object : Callback<List<StudentInfo>> {
                        override fun onResponse(
                            call: Call<List<StudentInfo>>,
                            response: Response<List<StudentInfo>>
                        ) {
                            runOnUiThread {
                                if (response.isSuccessful) {
                                    // Handle successful response
                                    val resultList: List<StudentInfo>? = response.body()
                                    if (!resultList.isNullOrEmpty()) {
                                        DataManager.studentInfo = resultList.first()
                                        val intent =
                                            Intent(this@CheckResult, MarkSPage1::class.java)
                                        startActivity(intent)
                                    } else {
                                        showToast("Empty response body")
                                    }
                                } else {
                                    handleApiError(response.code())
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<StudentInfo>>, t: Throwable) {
                            // Handle failure
                            Log.e("API Call", "Failed in getStudentInfo: ${t.message}")
                            showToast("API Call Failed: ${t.message}")
                        }
                    })
                } catch (e: NumberFormatException) {
                    showToast("Invalid Registration Number")
                }
            } else {
                showToast("Please enter both Registration Number and Date of Birth")
            }
        }
    }

    private fun handleApiError(errorCode: Int) {
        val errorMessage: String = when (errorCode) {
            400 -> "Invalid credentials. Please check your Registration Number and Date of Birth."
            else -> "API Call Failed with code: $errorCode"
        }
        Log.e("API Call", errorMessage)
        showToast(errorMessage)
    }

    private fun showToast(message: String) {
        showToast(this, message)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
