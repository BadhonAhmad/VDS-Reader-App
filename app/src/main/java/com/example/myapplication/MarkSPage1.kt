package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.myapplication.theme.*
import com.example.myapplication.theme.MyApplicationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarkSPage1 : ComponentActivity() {
    private val BASE_URL = "http://192.168.0.102:5001/"
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
                        setContent {
                            MyApplicationTheme() {
                                Surface()
                                {
                                    background(resultList ?: emptyList())
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






