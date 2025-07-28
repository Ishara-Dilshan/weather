package com.ishara.weather.clients;

import com.ishara.weather.dtos.WeatherResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "weather.api.url=http://localhost:8089/data/forecast?q=%s&appid=%s",
        "weather.api.key=key"
})
public class WeatherApiClientTest {

    private WireMockServer wireMockServer;

    @Autowired
    private WeatherApiClient weatherApiClient;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @Test
    void test_getWeather_forecast() {
        wireMockServer.stubFor(get(urlPathMatching("/data/forecast"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("weather_response.json")
                        .withStatus(200)));

        WeatherResponseDto response = weatherApiClient.getWeatherForecast("London");

        assertNotNull(response);
        assertEquals("200", response.getCod());
        assertFalse(response.getList().isEmpty());

        wireMockServer.stop();
    }
}