package org.kodigo.airport.flight;

import org.kodigo.airport.entities.IFlight;

import java.util.List;
import java.util.Map;

public interface IFlightFilter {
  List<IFlight> filterFlight();

  Map<IFlight, String> filterDescriptions(
      Map<IFlight, String> descriptions, List<IFlight> filteredFlights);

  IFlight getFlight();
}
