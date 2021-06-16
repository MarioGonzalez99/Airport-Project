package org.kodigo.airport.flight;

import org.kodigo.airport.entities.IAircraft;

public interface IFlightCreator {
  void createFlight();

  void addFlight(
      int flightNumber,
      String airline,
      IAircraft aircraft,
      String originCountry,
      String originCity,
      String destinationCountry,
      String destinationCity,
      String departureDate,
      String arrivalDate,
      String status);
}
