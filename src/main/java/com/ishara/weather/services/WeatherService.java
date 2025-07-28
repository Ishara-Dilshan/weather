package com.ishara.weather.services;

import com.ishara.weather.dtos.WeatherSummaryDto;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {

    /**
     * This method is used to fetch and summarize weather data
     * @param city
     * @return
     */
    CompletableFuture<WeatherSummaryDto> getWeatherSummary(String city);

}
