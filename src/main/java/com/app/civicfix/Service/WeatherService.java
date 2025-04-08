package com.app.civicfix.Service;

import com.app.civicfix.Api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String key = "e47ca82dc3b7cb22a7e21b2137dc9757";
    private static final String api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String final_Api = api.replace("CITY", city).replace("API_KEY", key);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(final_Api, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
