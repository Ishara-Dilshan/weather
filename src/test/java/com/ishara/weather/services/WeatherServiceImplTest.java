package com.ishara.weather.services;

import com.ishara.weather.clients.WeatherApiClient;
import com.ishara.weather.dtos.WeatherResponseDto;
import com.ishara.weather.dtos.WeatherSummaryDto;
import com.ishara.weather.dtos.WeatherResponseDto.WeatherItem;
import com.ishara.weather.exceptions.CityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceImplTest {

    private WeatherApiClient weatherApiClient;
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        weatherApiClient = mock(WeatherApiClient.class);
        weatherService = new WeatherServiceImpl(weatherApiClient);
    }

    @Test
    void testGetWeatherSummary_ReturnsCorrectSummary() throws Exception {
        // Test Data
        String city = "London";

        WeatherItem.Main main1 = new WeatherItem.Main();
        main1.setTemp(30.0);
        WeatherItem item1 = new WeatherItem();
        item1.setDt_txt(LocalDateTime.of(2025, 7, 27, 9, 0));
        item1.setMain(main1);

        WeatherItem.Main main2 = new WeatherItem.Main();
        main2.setTemp(25.0);
        WeatherItem item2 = new WeatherItem();
        item2.setDt_txt(LocalDateTime.of(2025, 7, 28, 9, 0));
        item2.setMain(main2);

        WeatherResponseDto mockResponse = new WeatherResponseDto();
        mockResponse.setList(List.of(item1, item2));
        mockResponse.setCod("200");
        mockResponse.setMessage("success");

        when(weatherApiClient.getWeatherForecast(city)).thenReturn(mockResponse);

        // Actions
        CompletableFuture<WeatherSummaryDto> future = weatherService.getWeatherSummary(city);
        WeatherSummaryDto summary = future.get();

        // Assert
        assertEquals(city, summary.getCity());
        assertEquals(27.5, summary.getAverageTemperature(), 0.1);
        assertEquals("2025-07-27", summary.getHottestDay());
        assertEquals("2025-07-28", summary.getColdestDay());
    }

    @Test
    void testGetWeatherSummary_CityNotFound_ShouldThrowException() {
        // Test Data
        String city = "UnknownCity";
        when(weatherApiClient.getWeatherForecast(city)).thenThrow(new RuntimeException("City not found"));

        // Action and Assert
        assertThrows(CityNotFoundException.class, () -> {
            weatherService.getWeatherSummary(city).join();
        });
    }
}