# 🌤️ Weather App - Spring Boot Application

A Spring Boot application that fetches, processes, and analyzes weather data for a given city using an external API (OpenWeatherMap). The application supports asynchronous processing, caching, and includes proper error handling and testing.

---

## 📌 Features

- Fetch 5-day weather forecast data for any city.
- Calculate:
  - Average temperature over the last 7 days.
  - Hottest and coldest day based on temperature.
- Expose a RESTful API endpoint:  
  `GET /weather?city={cityName}`
- Asynchronous execution using `@Async`.
- Caching with expiration (30 minutes) to reduce repeated API calls.
- Graceful error handling for invalid cities or external API issues.
- Unit tests for controller and service layers.

---

### 🔧 Configuration

Update the `application.properties` file with your API key:

```properties
weather.api.key=your_api_key
weather.api.base-url=https://api.openweathermap.org/data/2.5
