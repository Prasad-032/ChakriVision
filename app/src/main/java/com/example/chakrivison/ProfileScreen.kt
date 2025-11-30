package com.example.chakrivison

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun ProfileScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = auth.currentUser
    val userName = user?.displayName ?: "User"
    val userPhotoUrl = user?.photoUrl?.toString()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Profile.route
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
            MyAccountHeaderSection(
                userPhotoUrl = userPhotoUrl,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Large Profile Picture
                if (userPhotoUrl != null) {
                    AsyncImage(
                        model = userPhotoUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile_image),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Username with brackets
                Text(
                    text = "[$userName]",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Action Buttons
                ActionButton(
                    text = "HISTORY",
                    onClick = {
                        navController.navigate(Screen.History.route) {
                            popUpTo(Screen.Profile.route) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                ActionButton(
                    text = "ABOUT",
                    onClick = {
                        navController.navigate(Screen.About.route) {
                            popUpTo(Screen.Profile.route) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                ActionButton(
                    text = "CONTACT",
                    onClick = {
                        navController.navigate(Screen.Contact.route) {
                            popUpTo(Screen.Profile.route) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                ActionButton(
                    text = "LOGOUT",
                    onClick = {
                        auth.signOut()
                        navController.navigate(Screen.Landing.route) {
                            popUpTo(Screen.Landing.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MyAccountHeaderSection(
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

        // MY ACCOUNT Title
        Text(
            text = "MY ACCOUNT",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        // Profile Icon (Clickable - for consistency, though already on Profile)
        IconButton(
            onClick = {
                // Already on Profile screen, but keeping consistent behavior
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
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(56.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

