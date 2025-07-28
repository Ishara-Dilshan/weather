package com.ishara.weather.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WeatherResponseDto {
    private List<WeatherItem> list;
    private String cod;
    private String message;

    @Data
    public static class WeatherItem {
        private Main main;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dt_txt;

        @Data
        public static class Main {
            private double temp;
        }
    }
}
