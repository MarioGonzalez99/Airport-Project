package org.kodigo.airport.entities;

public interface IFlight {
  @Override
  String toString();

  int getFlightNumber();

  ICountry getCountry();

  ICity getCity();

  IDate getDateFlight();

  IFlightDetails getFlightDetails();
}
