# Mentorme Android Application

This project is an Android application built using modern Android technologies along with Firebase integration for cloud services.

## Overview

The application is structured as a multi-module Gradle project with nested settings. It leverages:

- **Kotlin** as the programming language.
- **AndroidX** libraries, including AppCompat, ConstraintLayout, RecyclerView, CardView, and Material Design.
- **Firebase** services for authentication, real-time database, storage, and messaging.
- **Gradle** for building, managing dependencies, and packaging the app.

## Tech Stack

- **Programming Language:** Kotlin ([Gradle Kotlin DSL](i210438/app/build.gradle.kts))
- **Build System:** Gradle with multi-module support ([top-level build.gradle.kts](i210438/build.gradle.kts), [Gradle wrapper properties](i210438/i210438/gradle/wrapper/gradle-wrapper.properties))
- **Android Support Libraries:**
  - `androidx.appcompat:appcompat` ([seen in i210438/app/build.gradle.kts](i210438/app/build.gradle.kts))
  - `androidx.constraintlayout:constraintlayout`
  - `androidx.recyclerview:recyclerview` and selection
  - `androidx.cardview:cardview`
  - `com.google.android.material:material`
- **Firebase:**
  - Authentication – `com.google.firebase:firebase-auth`
  - Database – `com.google.firebase:firebase-database`
  - Storage – `com.google.firebase:firebase-storage` & `firebase-storage-ktx`
  - Cloud Messaging – `com.google.firebase:firebase-messaging`
  
  The Firebase configuration is provided in [google-services.json](i210438/app/google-services.json).

- **Additional Libraries:**
  - [CircleImageView](i210438/app/build.gradle.kts) (`de.hdodenhof:circleimageview`)
  - Games support - `androidx.games:games-activity`

## Project Use Cases

- **User Authentication:**  
  Users can sign in using Firebase Auth, enabling secure access to app features.

- **Real-time Data:**  
  The app uses Firebase Database to offer live data updates.

- **Cloud Storage:**  
  Firebase Storage allows users to upload and download media or other files.

- **Push Notifications:**  
  Firebase Messaging is utilized to provide timely notifications to app users.

- **Dynamic UI:**  
  With enabled View Binding ([enabled in i210438/app/build.gradle.kts](i210438/app/build.gradle.kts)), the app efficiently binds views to reduce boilerplate code.

- **Modern App Design:**  
  Leveraging Material Design components from the Material library alongside AndroidX libraries provides a contemporary, responsive UI.

## Project Structure

The root of the workspace is organized as follows:

- **Root-level files:**  
  Global Gradle settings ([i210438/build.gradle.kts](i210438/build.gradle.kts)) and properties ([i210438/gradle.properties](i210438/gradle.properties)).

- **Android app module:**  
  - **Application source code:** Located in `i210438/app/src`
  - **App build configuration:** [i210438/app/build.gradle.kts](i210438/app/build.gradle.kts)
  - **ProGuard Rules:** Provided in [i210438/app/proguard-rules.pro](i210438/app/proguard-rules.pro)
  - **Firebase Configuration:** [i210438/app/google-services.json](i210438/app/google-services.json)

- **Gradle Wrapper:**  
  The wrapper scripts for both Unix and Windows are provided to ensure consistent Gradle versions and build environments ([i210438/gradlew.bat](i210438/gradlew.bat), [i210438/i210438/gradle/wrapper/gradle-wrapper.properties](i210438/i210438/gradle/wrapper/gradle-wrapper.properties)).

## Build & Run Instructions

### Prerequisites:

- [Java JDK](https://openjdk.org/) (with the `JAVA_HOME` environment variable set correctly).
- Android Studio or Visual Studio Code with Android development extensions.

### Build the Project:

#### Windows:

```sh
cd i210438
./gradlew.bat assembleDebug
