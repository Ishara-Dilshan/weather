package com.ishara.weather.clients;

import com.ishara.weather.dtos.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherApiClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${weather.api.url}")
    private String apiURL;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherResponseDto getWeatherForecast(String city) {
        String url = String.format(apiURL, city, apiKey);
        log.info("Calling getWeatherSummary API with city {}", city);

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherResponseDto.class)
                .block();
    }
}
