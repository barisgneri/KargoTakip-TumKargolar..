# Kargo Takip (Cargo Tracking)

A simple and lightweight cargo tracking application developed with **Compose Multiplatform**. This project showcases the power of sharing a single UI codebase across Android, iOS, and Desktop environments.

The app allows users to easily track their shipments from various cargo providers in one place.

## 📲 Download

<a href="https://play.google.com/store/apps/details?id=com.barisproduction.kargo">
    <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" alt="Get it on Google Play" height="80">
</a>

### Tech Stack
* **Framework:** Compose Multiplatform (UI Framework)
* **Language:** Kotlin
* **Architecture:** MVI (Model-View-Intent)
* **Dependency Injection:** Koin
* **Networking:** Ktor
* **Local Storage:** Room Database
* **Image Loading:** Coil 3
* **Navigation:** Jetpack Navigation (Compose)
* **Concurrency:** Coroutines & Flow

## 📂 Project Structure

The project follows the standard Compose Multiplatform structure:

* `composeApp`: The heart of the project. Contains **100% shared UI** and business logic (Android, iOS, Desktop).
* `androidMain`: Android-specific configuration.
* `iosMain`: iOS entry points.
* `desktopMain`: Desktop launcher configuration.

## 🚀 How to Run

1.  Clone the repository.
2.  Open in **Android Studio** (with KMP plugin installed).
3.  Select your target (Android App, Desktop, or iOS) and hit **Run**.

---
*Powered by Compose Multiplatform & Kotlin.*
