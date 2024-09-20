package com.example.myapplication
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

fun printHelloWorld() {
    println("Hello, World!")
}


/**
 * Retrofit API service interface for making network requests.
 */
interface ApiService {

    /**
     * GET request to /getResults to retrieve a list of Getdata objects.
     *
     * @return Call object for the asynchronous response containing a list of Getdata.
     */
    @GET("StudentInfo")
    fun getStudentInfo(@Query("reg_no") regNo: Int,@Query("dateofbirth") dateofbirth:String): Call<List<StudentInfo>>
    @GET("getResults/{semester}")
    fun getResults(
        @Path("semester") semester: String,
        @Query("reg_no") regNo: Int
    ): Call<List<Getdata>>
}


