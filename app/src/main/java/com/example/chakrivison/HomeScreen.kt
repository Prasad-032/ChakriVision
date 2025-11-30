package com.example.chakrivison

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun HomeScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = auth.currentUser
    val userName = user?.displayName ?: "User"
    val userEmail = user?.email ?: ""
    val userPhotoUrl = user?.photoUrl?.toString()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Home.route
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
            HeaderSection(
                userPhotoUrl = userPhotoUrl,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Welcome Section
            WelcomeSection(
                userName = userName,
                userEmail = userEmail,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            // Course Cards Section
            CourseCardsSection(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun HeaderSection(
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

        // COURSES Title
        Text(
            text = "COURSES",
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
fun WelcomeSection(
    userName: String,
    userEmail: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi, $userName! 👋",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Welcome back",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        if (userEmail.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userEmail,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CourseCardsSection(
    modifier: Modifier = Modifier
) {
    val courses = listOf(
        Course("Course Title", "Short description", "₹499"),
        Course("Course Title", "Short description", "₹499"),
        Course("Course Title", "Short description", "₹499")
    )

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(courses) { course ->
            CourseCard(course = course)
        }
    }
}

data class Course(
    val title: String,
    val description: String,
    val price: String
)

@Composable
fun CourseCard(
    course: Course,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
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
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = course.description,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = course.price,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Button(
                    onClick = { /* Handle buy action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "BUY",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color.LightGray,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = { 
                Text("🏠", fontSize = 24.sp)
            },
            label = { Text("Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = { 
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        )
        NavigationBarItem(
            icon = { 
                Text("🔍", fontSize = 24.sp)
            },
            label = { Text("Search") },
            selected = currentRoute == Screen.Search.route,
            onClick = { 
                navController.navigate(Screen.Search.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        )
        NavigationBarItem(
            icon = { 
                Text("📖", fontSize = 24.sp)
            },
            label = { Text("My Courses") },
            selected = currentRoute == Screen.MyCourses.route,
            onClick = { 
                navController.navigate(Screen.MyCourses.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        )
        NavigationBarItem(
            icon = { 
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.Gray
                )
            },
            label = { Text("Profile") },
            selected = currentRoute == Screen.Profile.route,
            onClick = { 
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        )
    }
}

