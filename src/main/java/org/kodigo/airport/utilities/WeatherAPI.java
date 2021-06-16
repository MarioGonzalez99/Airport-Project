package org.kodigo.airport.utilities;

public class WeatherAPI implements IWeatherAPI {
  @Override
  public String getWeather() {
    var mockWeatherAPI = new MockWeatherAPI();
    return mockWeatherAPI.getRandomWeather();
  }
}
