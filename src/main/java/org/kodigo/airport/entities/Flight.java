package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Flight implements IFlight {
  private final int flightNumber;
  private final IFlightDetails flightDetails;
  private final ICountry country;
  private final ICity city;
  private final IDate dateFlight;

  @Override
  public String toString() {

    var leftAlignFormat =
        "| %-5s | %-9s | %-11s | %-15s | %-12s | %-16s | %-20s | %-17s | %-16s | %-8s |";

    return String.format(
        leftAlignFormat,
        getFlightNumber(),
        flightDetails.getAirline(),
        flightDetails.getAircraft().getModel(),
        country.getOriginCountry(),
        city.getOriginCity(),
        dateFlight.getDates().get(0),
        country.getDestinationCountry(),
        city.getDestinationCity(),
        dateFlight.getDates().get(1),
        flightDetails.getStatus());
  }
}
