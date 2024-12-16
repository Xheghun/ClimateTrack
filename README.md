# Climate Track
A weather app build with Jetpack Compose, with focus on clean & multi-module architecture.

### Setup 
 - First get an API key from [The Weather API](https://www.weatherapi.com/docs/)
 - In the project root create a `credentials.properties` file.
 - Create a `WEATHER_API_KEY` variable and set the value as your api key
 ```properties
WEATHER_API_KEY = "<YOUR-API-KEY>"
```
that's it! you can now build and run the project.

## Project Structure
This app was build to demonstrate clean architecture and multi-module development in android.

- data: This module contains the classes communicating with REST APIs, local caches, mappers and repository implementation. 
- domain: this is an independent module, containing the repository definition, models, and use-cases.
- presentation: contains, ui related classes, `composables`, and resources.
- app: this is the apps entry point, holding activities, koinModule initialization, and android build configurations.

## Artefact
[APK](https://drive.google.com/file/d/1lbkOm9a-Lyqh9Tnl-T8q6ToFiB9-zDV3/view?usp=sharing)


https://github.com/user-attachments/assets/1c1db12c-8265-47cb-a7af-cf1c930f299b

