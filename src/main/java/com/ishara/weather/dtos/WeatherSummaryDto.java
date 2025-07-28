package com.ishara.weather.dtos;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class WeatherSummaryDto {

    private String city;
    private double averageTemperature;
    private String hottestDay;
    private String coldestDay;

}
