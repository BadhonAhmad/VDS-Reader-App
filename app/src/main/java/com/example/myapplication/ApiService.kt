package com.example.myapplication
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
* Retrofit API service interface for making network requests.
*/
interface ApiService {

    /**
     * POST request to /getdata to send information.
     *
     * @param sendData Data to be sent in the request body.
     * @return Call object for the asynchronous response.
     */
    @POST("getdata")
    fun sendinfo(@Body sendData: SendData): Call<SendData>

    /**
     * GET request to /getResults to retrieve a list of Getdata objects.
     *
     * @return Call object for the asynchronous response containing a list of Getdata.
     */
    @POST("StudentInfo")
    fun getStudentInfo(@Body request: SendData): Call<List<StudentInfo>>
    @GET("getResults1")
    fun getResults1(@Query("reg_no") regNo: Int): Call<List<Getdata>>
    @GET("getResults2")
    fun getResults2() : Call<List<Getdata>>
    @GET("getResults1")
    fun getResults3() : Call<List<Getdata>>

    @GET("getResults1")
    fun getResults4() : Call<List<Getdata>>

    @GET("getResults1")
    fun getResults5() : Call<List<Getdata>>

    @GET("getResults1")
    fun getResults6() : Call<List<Getdata>>

    @GET("getResults1")
    fun getResults7() : Call<List<Getdata>>

    @GET("getResults1")
    fun getResults8() : Call<List<Getdata>>
}



