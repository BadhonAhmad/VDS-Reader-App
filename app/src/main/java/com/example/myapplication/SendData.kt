package com.example.myapplication
/**
 * Data class representing the data to be sent in a request.
 *
 * @param reg_no Registration number of the user.
 * @param dateOfbirth Date of birth of the user.
 */
data class SendData(
    val reg_no:Int,
    val dateofbirth:String
)
