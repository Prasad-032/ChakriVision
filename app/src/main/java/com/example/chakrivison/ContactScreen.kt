package com.example.chakrivison

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ContactScreen(
    auth: FirebaseAuth,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Contact.route
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
            ContactHeaderSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Main Content - Contact Buttons
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // LINKEDIN Button
                ContactButton(
                    text = "LINKEDIN",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/chakri-kotakala-453188251/"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // YOUTUBE Button
                ContactButton(
                    text = "YOUTUBE",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@chakrisvision?si=HDkJD4cA1zoehDtp"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // MAIL Button
                ContactButton(
                    text = "MAIL",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:chakrisvision@gmail.com")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // TELEGRAM Button
                ContactButton(
                    text = "TELEGRAM",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/chakrisvision"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // TWITTER (X) Button
                ContactButton(
                    text = "TWITTER (X)",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://x.com/ChakrisVision?t=Ls1OrC-VU-NX5x0pB96Obw&s=09"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // INSTAGRAM Button
                ContactButton(
                    text = "INSTAGRAM",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/chakris_vision?igsh=eXd0OTJxMWxvM3U5"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // WEBSITE Button
                ContactButton(
                    text = "WEBSITE",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://runovaai.netlify.app/"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
fun ContactHeaderSection(
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
        // CONTACT Title (Centered, no profile elements)
        Text(
            text = "CONTACT",
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
fun ContactButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFE5E5) // Light pink background
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

