package org.kodigo.airport.entities;

public interface IFlight {
  @Override
  String toString();

  int getFlightNumber();

  ICountry getCountry();

  ICity getCity();

  IDate getDateFlight();

  String getStatus();

  String getAirline();

  IAircraft getAircraft();

  void setStatus(String status);

  void setAircraft(IAircraft aircraft);
}
