package com.ishara.weather.controllers;

import com.ishara.weather.dtos.WeatherSummaryDto;
import com.ishara.weather.services.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("${spring.data.rest.base-path}/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public CompletableFuture<WeatherSummaryDto> getWeather(@RequestParam String city) {
        log.info("Calling getWeather Method with city {}", city);
        return weatherService.getWeatherSummary(city);
    }
}
