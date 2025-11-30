package com.example.chakrivison

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chakrivison.ui.theme.ChakriVisonTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ChakriVisonTheme {
                AppNavigation(auth = auth)
            }
        }
    }
}

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    onSignInSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isInPreview = LocalInspectionMode.current

    val oneTapClient: SignInClient = remember { Identity.getSignInClient(context) }
    val serverClientId = stringResource(id = R.string.server_client_id)

    // ✔ Correct One Tap sign-in request
    val signInRequest = remember(serverClientId) {
        BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(serverClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    // ✔ Launcher for One Tap result
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            coroutineScope.launch {
                try {
                    val credential =
                        oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken

                    if (idToken != null) {
                        val firebaseCredential =
                            GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("FirebaseAuth", "Sign-in successful")
                                    onSignInSuccess()
                                } else {
                                    Log.e(
                                        "FirebaseAuth",
                                        "Firebase sign-in failed",
                                        task.exception
                                    )
                                }
                            }
                    }
                } catch (e: ApiException) {
                    Log.e("GoogleSignIn", "Error: ${e.localizedMessage}")
                }
            }
        }
    }

    // UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF00A3E0), fontWeight = FontWeight.Bold)) {
                    append("LEARN\n")
                }
                withStyle(SpanStyle(color = Color(0xFF005A9C), fontWeight = FontWeight.Bold)) {
                    append("SMARTER.\n")
                }
                withStyle(SpanStyle(color = Color(0xFF00A3E0), fontWeight = FontWeight.Bold)) {
                    append("GROW\n")
                }
                withStyle(SpanStyle(color = Color(0xFF005A9C), fontWeight = FontWeight.Bold)) {
                    append("FASTER.")
                }
            },
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile_image),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Google Sign-in button
        Button(
            onClick = {
                if (isInPreview) return@Button

                coroutineScope.launch {
                    try {
                        val result = oneTapClient.beginSignIn(signInRequest).await()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                result.pendingIntent.intentSender
                            ).build()
                        )
                    } catch (e: Exception) {
                        Log.e("GoogleSignIn", "Sign-in failed: ${e.localizedMessage}")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.border(
                1.dp,
                Color.Gray,
                shape = MaterialTheme.shapes.medium
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Sign in with Google", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LandingScreenPreview() {
    ChakriVisonTheme {
        // Use actual FirebaseAuth instance for preview
        // In preview mode, this won't make actual network calls
        val auth = FirebaseAuth.getInstance()
        LandingScreen(auth = auth)
    }
}
