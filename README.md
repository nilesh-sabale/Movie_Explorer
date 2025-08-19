# Movie Explorer App

Movie Explorer is an Android application that allows users to search for movies, sort them by release date, and save favorites locally using SharedPreferences. The app fetches movie data from the **OMDb API**.

## Features

- **Search Movies**: Enter a movie name to search via OMDb API.
- **Sort Movies**: Sort search results by release date (latest or oldest).
- **Favorites**: Save your favorite movies locally using SharedPreferences.


# Tech Stack
Language: Java / Kotlin (specify which)
Android SDK: API 26+

# Libraries:
Retrofit – for API calls
Gson – for parsing JSON
Glide – for image loading
RecyclerView – for displaying movies

# Storage: SharedPreferences for saving favorites

# Setup Instructions
Clone the Repository
git clone https://github.com/nilesh-sabale/Movie_Explorer.git

# Open in Android Studio
File → Open → Select project folder.
Let Gradle sync automatically.
Configure OMDb API Key
Get your API key from OMDb API
Open ApiClient.java (or equivalent file) and replace:
private static final String API_KEY = "YOUR_API_KEY";
with your actual API key.
Build and Run

Connect an Android device or start an emulator.
Click Run in Android Studio.

# Usage
Enter a movie name in the search bar.
Tap Search to see results.
Sort results by latest or oldest using the sort option.
Tap the heart icon to add a movie to favorites.
View favorites in the Favorites section.

# Contributing
Fork the repository.
Create a feature branch: git checkout -b feature-name
Commit changes: git commit -m "Add feature"
Push branch: git push origin feature-name
Open a Pull Request.

# Contact
Developer: Nilesh Sabale
GitHub: https://github.com/nilesh-sabale
