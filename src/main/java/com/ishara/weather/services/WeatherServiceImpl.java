package com.ishara.weather.services;

import com.ishara.weather.clients.WeatherApiClient;
import com.ishara.weather.dtos.WeatherResponseDto;
import com.ishara.weather.dtos.WeatherSummaryDto;
import com.ishara.weather.exceptions.CityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiClient weatherApiClient;

    @Async
    @Cacheable(value = "weather", key = "#city")
    public CompletableFuture<WeatherSummaryDto> getWeatherSummary(String city) {

        try {
            WeatherResponseDto response = weatherApiClient.getWeatherForecast(city);

            //Grouping data in to days
            Map<LocalDate, List<Double>> groupedTemps = response.getList().stream()
                    .collect(Collectors.groupingBy(
                            item -> item.getDt_txt().toLocalDate(),
                            Collectors.mapping(item -> item.getMain().getTemp(), Collectors.toList())
                    ));

            //Put to map with Date
            Map<LocalDate, Double> dailyAverages = new HashMap<>();
            for (Map.Entry<LocalDate, List<Double>> entry : groupedTemps.entrySet()) {
                double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                dailyAverages.put(entry.getKey(), avg);
            }

            LocalDate hottest = Collections.max(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();
            LocalDate coldest = Collections.min(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();

            double avgTemp = dailyAverages.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            return CompletableFuture.completedFuture(
                    new WeatherSummaryDto(city, avgTemp, hottest.toString(), coldest.toString())
            );
        } catch (Exception e) {
            log.error("Error fetching weather: ", e);
            throw new CityNotFoundException("City not found or API error for city : " + city);
        }
    }
}
