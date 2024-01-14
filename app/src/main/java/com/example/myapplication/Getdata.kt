package com.example.myapplication
/**
* Data class representing the output of a query for student examination results.
*
* @param reg_no Registration number of the student.
* @param GPA Grade Point of the student.
* @param Grade Grade obtained by the student.
* @param course_code Course code for the particular examination result.
* @param course_title Title of the course for the examination result.
* @param semester Semester in which the examination was taken.
* @param course_credit Credits assigned to the course for the examination result.
*/

data class  Getdata (
    val reg_no: Int,
    val GPA:Double,
    val Grade: String,
    val course_code : String,
    val course_title: String,
    val semester : String,
    val course_credit: Float
)