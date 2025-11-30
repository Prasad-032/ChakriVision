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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyCoursesScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = auth.currentUser
    val userPhotoUrl = user?.photoUrl?.toString()
    
    // Sample purchased courses - in a real app, this would come from a data source
    // For now, we'll use a simple list. In production, this would be fetched from
    // Firebase/database based on user's purchase history
    val purchasedCourses = remember {
        listOf(
            Course("Android Development", "Learn to build Android apps with Kotlin", "₹499"),
            Course("Web Development", "Master HTML, CSS, and JavaScript", "₹499"),
            Course("Data Science", "Python, Machine Learning, and Analytics", "₹499")
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.MyCourses.route
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
            MyLearningHeaderSection(
                userPhotoUrl = userPhotoUrl,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // MY COURSES Section Title
            Text(
                text = "MY COURSES",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Purchased Courses List
            PurchasedCoursesSection(
                courses = purchasedCourses,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun MyLearningHeaderSection(
    userPhotoUrl: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture
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

        // MY LEARNING Title
        Text(
            text = "MY LEARNING",
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
fun PurchasedCoursesSection(
    courses: List<Course>,
    modifier: Modifier = Modifier
) {
    if (courses.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No purchased courses yet",
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

@Composable
fun PurchasedCourseCard(
    course: Course,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        // Background Image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1a1a2e),
                            Color(0xFF16213e),
                            Color(0xFF0f3460)
                        )
                    )
                )
        ) {
            // Tech-themed background pattern (simplified)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF00ff88).copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = course.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = course.description,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = { /* Handle access action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "ACCESS",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

