package com.example.chakrivison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AboutScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.About.route
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            // Header Section
            AboutHeaderSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Main Content - Scrollable Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                AboutContentCard()
            }
        }
    }
}

@Composable
fun AboutHeaderSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ABOUT Title (Centered, no profile elements)
        Text(
            text = "ABOUT",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun AboutContentCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE5E5) // Light pink background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(
                text = "About Chakri's Vision",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // First Paragraph
            Text(
                text = "Chakri's Vision is designed with a simple yet powerful mission — to help students learn technical skills and stay updated with emerging technologies. In today's fast-changing world, we understand the challenges students face in keeping up with their studies, understanding concepts deeply, and preparing for future careers.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Second Paragraph
            Text(
                text = "Our app aims to bridge this gap by offering clear, practical, and hands-on learning experiences. Whether you're exploring the fundamentals or diving into advanced tech, Chakri's Vision guides you every step of the way, helping you overcome learning hurdles and move confidently towards a successful career. Learn smarter. Grow faster. Build your future with Chakri's Vision.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

