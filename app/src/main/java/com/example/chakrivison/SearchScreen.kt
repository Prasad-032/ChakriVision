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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun SearchScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = auth.currentUser
    val userPhotoUrl = user?.photoUrl?.toString()
    
    var searchQuery by remember { mutableStateOf("") }
    
    // Sample courses list - in a real app, this would come from a data source
    val allCourses = remember {
        listOf(
            Course("Android Development", "Learn to build Android apps with Kotlin", "₹499"),
            Course("Web Development", "Master HTML, CSS, and JavaScript", "₹499"),
            Course("Data Science", "Python, Machine Learning, and Analytics", "₹499"),
            Course("UI/UX Design", "Create beautiful user interfaces", "₹499"),
            Course("Flutter Development", "Build cross-platform mobile apps", "₹499"),
            Course("React Native", "Develop mobile apps with React", "₹499")
        )
    }
    
    // Filter courses based on search query
    val filteredCourses = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allCourses
        } else {
            allCourses.filter { course ->
                course.title.contains(searchQuery, ignoreCase = true) ||
                course.description.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Search.route
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
            SearchHeaderSection(
                userPhotoUrl = userPhotoUrl,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            // Course Cards Section
            CourseCardsSection(
                courses = filteredCourses,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun SearchHeaderSection(
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

        // SEARCH COURSES Title
        Text(
            text = "SEARCH COURSES",
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
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            Text(
                text = "SEARCH COURSES...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 16.sp,
            color = Color.Black
        )
    )
}

@Composable
fun CourseCardsSection(
    courses: List<Course>,
    modifier: Modifier = Modifier
) {
    if (courses.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No courses found",
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
                CourseCard(course = course)
            }
        }
    }
}

