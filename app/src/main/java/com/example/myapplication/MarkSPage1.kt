package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.theme.MyApplicationTheme
import com.example.myapplication.theme.background
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarkSPage1 : ComponentActivity() {
    private val BASE_URL = "http://10.200.192.79:5001/"
    private var markSPage2Launched = false
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make the API call
        val call: Call<List<Getdata>> = apiService.getResults()

        call.enqueue(object : Callback<List<Getdata>> {
            override fun onResponse(call: Call<List<Getdata>>, response: Response<List<Getdata>>) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        // Handle successful response
                        val resultList: List<Getdata>? = response.body()
                        if (resultList != null) {
                            setContent {
                                MyApplicationTheme {
                                    Surface {
                                        val navController = rememberNavController()
                                        NavHost(
                                            navController = navController,
                                            startDestination = "Start"
                                        ) {
                                            composable("Start") {

                                                background(resultList, navController)
                                            }

                                            composable("nextpage") {
                                                // Check if MarkSPage2 has already been launched
                                                if (!markSPage2Launched) {
                                                    // Launch MarkSPage2
                                                    val intent = Intent(this@MarkSPage1, MarkSPage2::class.java)
                                                    startActivity(intent)

                                                    // Set the flag to true to indicate that MarkSPage2 has been launched
                                                    markSPage2Launched = true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        handleApiError(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<List<Getdata>>, t: Throwable) {
                runOnUiThread {
                    // Handle failure
                    handleApiError(t.message ?: "Unknown error")
                }
            }
        })
    }

    private fun handleApiError(error: Any?) {
        val errorMessage = "API call failed. Error: $error"
        Log.e("API Call", errorMessage)

        // Show a Toast with the error message
        showToast(errorMessage)
    }

    private fun showToast(message: String) {
        showToast(this@MarkSPage1, message)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
