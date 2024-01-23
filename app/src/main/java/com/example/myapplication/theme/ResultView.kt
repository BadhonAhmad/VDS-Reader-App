package com.example.myapplication.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.CheckResult
import com.example.myapplication.Getdata
import com.example.myapplication.R

/**
 * Composable function to create the background layout for displaying examination results.
 *
 * @param results List of examination results to be displayed.
 */
@Composable

fun background(results: List<Getdata>, navController: NavController,semester:String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        item {
            // Header Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sustlogo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }

        item{
            // University Title
            Text(
                text = "SHAHJALAL UNIVERSITY OF SCIENCE & TECHNOLOGY, SYLHET, BANGLADESH",
                fontSize = 11.5.sp,
                textAlign = TextAlign.Center
            )
        }

        item{
            // Student Information

               // contentAlignment= Alignment.Center


            GetPersonInfo(
                name = CheckResult.DataManager.studentInfo?.name ?: "",
                regName = CheckResult.DataManager.studentInfo?.reg_no.toString()?: "", // Corrected typo
                session = CheckResult.DataManager.studentInfo?.session ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )


        }

        item {
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Box(
                contentAlignment= Alignment.Center
            ) {
                // Examination Title
                Text(
                    text = "${semester} Semester Examination",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }
        }

        // Result Table
        item {
            TableHeader()
        }
        items(results) { result ->
            ResultItem(result = result)
        }


        item {
            ElevatedButton(
                onClick = {
                    // Navigate to the next page if the destination exists
                    navController.navigate("nextpage") {
                        popUpTo("Start") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)  // Adjusted button height
            ) {
                Text("NEXT")
            }
        }
    }
}
@Preview
@Composable
fun Pre(){
    background(emptyList(), rememberNavController(),"1st")
}


/**
 * Composable function to display an individual examination result item.
 *
 * @param result Individual examination result.
 */
@Composable
fun ResultItem(result: Getdata) {
    TableRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TableCell(text = result.course_code, weight = 0.21f, alignment = TextAlign.Left, title = false)
        TableCell(text = result.course_title, weight = 0.265f, alignment = TextAlign.Left, title = false)
        TableCell(text = result.course_credit.toString(), weight = 0.185f, alignment = TextAlign.Left, title = false)
        TableCell(text = result.GPA.toString(), weight = 0.155f, alignment = TextAlign.Left, title = false)
        TableCell(text = result.Grade, weight = 0.185f, alignment = TextAlign.Left, title = false)
    }
}

/**
 * Composable function to create a row in the table layout.
 *
 * @param modifier Modifier for the row.
 * @param content Row content.
 */
@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer),
        content = content
    )
}

/**
 * Composable function to create a table cell within a row.
 *
 * @param text Text content of the cell.
 * @param weight Weight of the cell.
 * @param alignment Text alignment within the cell.
 * @param title Indicates whether the cell is a title cell.
 */
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    alignment: TextAlign = TextAlign.Center,
    title: Boolean = false,
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(10.dp),
        fontWeight = if (title) FontWeight.Bold else FontWeight.Normal,
        textAlign = alignment,
        fontSize = 11.sp
    )
}

/**
 * Composable function to create the header row of the result table.
 */
@Composable
fun TableHeader() {
    TableRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
    ) {
        TableCell(text = "Course No.", weight = 0.21f, alignment = TextAlign.Left, title = true)
        TableCell(text = "Course Title", weight = 0.265f, alignment = TextAlign.Left, title = true)
        TableCell(text = "Credit", weight = 0.185f, alignment = TextAlign.Left, title = true)
        TableCell(text = "Grade Point.", weight = 0.155f, alignment = TextAlign.Left, title = true)
        TableCell(text = "Letter Grade", weight = 0.185f, alignment = TextAlign.Left, title = true)
    }
}

/**
 * Composable function to display the entire result table.
 *
 * @param results List of examination results to be displayed in the table.
 */
@Composable
fun ResultTable(results: List<Getdata>) {
    LazyColumn {
        item {
            TableHeader()
        }
        items(results) { result ->
            ResultItem(result = result)
        }
    }
}

    /**
     * Composable function to display personal information of a student.
     *
     * @param name Student's name.
     * @param regName Student's registration name.
     * @param session Session information.
     */
    @Composable
    fun GetPersonInfo(name: String, regName: String, session: String, modifier: Modifier) {

        Text(
            text = "Name of Student :$name",
            fontSize = 10.5.sp,
        )
        Text(
            text = "Registration No :$regName",
            fontSize = 10.5.sp
        )
        Text(
            text = "Session :$session",
            fontSize = 10.5.sp
        )

    }


