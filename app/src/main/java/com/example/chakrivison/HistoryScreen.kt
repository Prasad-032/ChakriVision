package com.example.chakrivison

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HistoryScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = auth.currentUser
    val userPhotoUrl = user?.photoUrl?.toString()
    
    // Sample completed/taken courses - in a real app, this would come from a data source
    // For now, we'll use a simple list. In production, this would be fetched from
    // Firebase/database based on user's course completion/taking history
    val completedCourses = remember {
        listOf(
            Course("Android Development", "Learn to build Android apps with Kotlin", "₹499"),
            Course("Web Development", "Master HTML, CSS, and JavaScript", "₹499")
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.History.route
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Header Section
            HistoryHeaderSection(
                userPhotoUrl = userPhotoUrl,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Descriptive Text
            Text(
                text = "courses completed or course taken are displayed here",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                textAlign = TextAlign.Left
            )

            // Completed Courses List
            CompletedCoursesSection(
                courses = completedCourses,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun HistoryHeaderSection(
    userPhotoUrl: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Small Profile Picture
        if (userPhotoUrl != null) {
            AsyncImage(
                model = userPhotoUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        // HISTORY Title
        Text(
            text = "HISTORY",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        // Profile Icon (Clickable)
        IconButton(
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(32.dp),
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun CompletedCoursesSection(
    courses: List<Course>,
    modifier: Modifier = Modifier
) {
    if (courses.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No completed courses yet",
                fontSize = 18.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(courses) { course ->
                PurchasedCourseCard(course = course)
            }
        }
    }
}

