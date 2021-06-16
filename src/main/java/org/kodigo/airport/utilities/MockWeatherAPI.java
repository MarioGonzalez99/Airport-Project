package org.kodigo.airport.utilities;

import java.util.Random;

// Simulated Weather API
public class MockWeatherAPI {

    private final Random rand = new Random();

    String getRandomWeather() {
        var weather = "";
        var randomNum = rand.nextInt(7);
        weather = switch (randomNum) {
            case 0 -> "Warm 30 ºC Wind 11KM N";
            case 1 -> "Heavy Rain 20 ºC Wind 11KM N";
            case 2 -> "Light Rain 22 ºC Wind 11KM N";
            case 3 -> "Cloudy 25 ºC Wind 11KM S";
            case 4 -> "Thunderstorm 15 ºC Wind 30KM S";
            case 5 -> "Snow -3 ºC Wind 5KM E";
            case 6 -> "Moderate Rain 23 ºC Wind 24KM N";
            default -> "API couldn't recover weather value";
        };
        return weather;
    }
}
