package com.app.civicfix.Controller;

import com.app.civicfix.Api.response.WeatherResponse;
import com.app.civicfix.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService service;

    @GetMapping
    public ResponseEntity<?> getWeatherDetails(){
        WeatherResponse weather=service.getWeather("pune");
        String greetings="";
        if(weather!=null){
            greetings="hello, the weather feels like "+weather.getCurrent().getWeatherDescriptions().getFirst()+"\nTemperature is:- "+weather.getCurrent().getTemperature();
        }
        return new ResponseEntity<>("Hi "+greetings, HttpStatus.OK);
    }
}
