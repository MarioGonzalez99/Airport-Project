package org.kodigo.airport.flight;

import org.kodigo.airport.entities.*;

public interface IFlightCreator {
  void createFlight();

  void addFlight(IFlight flight);
}
