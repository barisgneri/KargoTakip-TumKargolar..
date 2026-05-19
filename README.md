# 📦 Cargo Tracker (Kargo Takip)

A cross-platform mobile application designed to query and track shipment statuses from various logistics and cargo companies in a unified dashboard. 

The project is built entirely with **Compose Multiplatform**, sharing a single codebase for UI, domain, and data layers to deliver native performance on both Android and iOS platforms.

## 📲 Download

<a href="https://play.google.com/store/apps/details?id=com.barisproduction.kargo">
    <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" alt="Get it on Google Play" height="80">
</a>

## Features

* **Multi-Carrier Tracking:** Track and manage packages from different logistics companies in one centralized list.
* **Cross-Platform UI:** 100% shared user interface and business logic for both Android and iOS.
* **Offline Support:** Local caching and data persistence using KMP-compatible Room SQLite.
* **Predictable State Management:** Strict adherence to the **MVI (Model-View-Intent)** architecture with custom delegation.
* **Automated CI/CD:** Automated build, testing, and deployment pipelines powered by GitHub Actions.

## Tech Stack

* **Multiplatform Framework:** Compose Multiplatform (KMP)
* **Architecture:** MVI (Model-View-Intent), Clean Architecture approach
* **Dependency Injection:** Koin
* **Networking:** Ktor Client
* **Local Database:** Room (KMP Support)
* **Concurrency:** Kotlin Coroutines & Flows
* **Navigation:** Compose Navigation
* **CI/CD:** GitHub Actions

## Project Structure

The repository is structured to maximize code sharing across platforms, keeping platform-specific code to an absolute minimum:

```text
CargoTracker
├── composeApp/
│   ├── src/androidMain/     # Android-specific implementations (e.g., Clipboard Service, DB Driver)
│   ├── src/iosMain/         # iOS-specific implementations
│   └── src/commonMain/      # Shared UI, ViewModels, Domain, and Data layers
│       ├── common/          # Extensions, Constants, and base Resource states
│       ├── data/            # Remote (Ktor), Local (Room) sources, and Repositories
│       ├── delegation/      # Custom MVI delegate interfaces and implementations
│       ├── di/              # Koin dependency injection modules
│       ├── domain/          # Models and UseCases (e.g., CheckForceUpdateUseCase)
│       ├── navigation/      # Multiplatform navigation graph
│       └── ui/              # Feature-based UI components (Tracking, CargoList, AddCargo)
├── iosApp/                  # Xcode project for iOS build
└── .github/workflows/       # CI/CD pipelines (release.yml)
