# ChakriVision

**ChakriVision** is a modern Android application built using **Kotlin** and **Jetpack Compose**.  
The current phase focuses on a polished landing page with secure Google authentication, forming a solid foundation for a full-featured learning app.

---

## **Project Overview**

- Clean and modern landing page UI using Jetpack Compose  
- Secure Google Sign-In integration with Firebase Authentication  
- Persistent login for seamless user experience  
- Compose Previews with mock objects for safe UI testing  
- Modular architecture ready for future navigation and course features  

---

## **Key Features**

- **Modern UI with Jetpack Compose**
  - Declarative UI (no XML layouts)
  - Dual-tone headline: *"LEARN SMARTER. GROW FASTER."*  
  - Circular profile image with `Clip(CircleShape)`  
  - Custom Google Sign-In button with branding  

- **Authentication**
  - Google One Tap Sign-In using **Google Identity Services (GIS)**
  - FirebaseAuth integration for backend authentication  
  - Persistent login check via `auth.currentUser`

- **Developer Experience**
  - Compose Previews using `LocalInspectionMode` and Mockito mocks  
  - Clean and maintainable project structure  

---

## **Tech Stack**

- Kotlin (Android app development)  
- Jetpack Compose (UI framework)  
- Firebase Authentication (backend)  
- Google Identity Services (GIS)  
- Android Studio Giraffe/Koala  
- Mockito (UI previews)  

---

## **Screenshots / Demo**

> *You can add your app screenshots here.*

1. Landing Page UI  
2. Google Sign-In prompt  
3. Debug Preview Layout  

---

## **Installation / Running Locally**

1. Clone the repository:

```bash
git clone https://github.com/Prasad-032/ChakriVision.git
cd ChakriVision

2.Open in Android Studio

3.Add your google-services.json file in the app/ folder

4.Build and run the project on an emulator or physical device

Note: Do not commit google-services.json for security reasons.

###Authentication Flow

- Landing Screen → Display a “Sign in with Google” button.
- Trigger One Tap Google Sign-In → Use Google Identity Services (GIS) for authentication.
- Exchange ID Token → Pass the Google ID token to Firebase and obtain a Firebase credential.
- Persistent Login → Use auth.currentUser to maintain the authenticated session.
- Successful Login → Navigate the user to HomeScreen (to be implemented next).

###Challenges & Solutions

- Deprecated APIs → Migrated to Google Identity Services (GIS) builder pattern for modern compliance.
- Dependency Issues → Reverted to FirebaseAuth.getInstance() to ensure stability and avoid breaking changes.
- Composable Previews → Added LocalInspectionMode and Mockito mocks for smoother preview rendering and testing.
- Syntax & Imports → Cleaned up MainActivity.kt and corrected incorrect imports for better maintainability.

###Next Steps / Future Scope

- Add Navigation Component for multiple screens
- Create HomeScreen with welcome message and logout
- Implement Course Content module
- Optional: Firestore database integration for dynamic courses


###Learnings

- Jetpack Compose UI design patterns
- Modern Google Identity Services authentication
- Managing Firebase dependencies safely
- Proper project structure for maintainability
- Handling Compose previews without crashing

###Contact / Credits

Developer: KILLAMPUDI DEVIVARAPRASAD

GitHub: https://github.com/Prasad-032

Email: killampudidevivaraprasad@gmail.com
