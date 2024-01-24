package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

class MarkSPage8 : ComponentActivity() {
    private val BASE_URL = "http://192.168.29.116:5001/"
    private var markSPage2Launched = false
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    private val regNo= CheckResult.DataManager.studentInfo?.reg_no.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make the API call
        val call: Call<List<Getdata>> = apiService.getResults("8th",regNo.toInt())

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
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            item {
                                                background(resultList, "8th")

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
        showToast(this@MarkSPage8, message)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}