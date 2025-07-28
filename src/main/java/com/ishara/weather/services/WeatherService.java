package com.ishara.weather.services;

import com.ishara.weather.dtos.WeatherSummaryDto;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {

    CompletableFuture<WeatherSummaryDto> getWeatherSummary(String city);

}
