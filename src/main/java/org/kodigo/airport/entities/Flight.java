package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Flight implements IFlight {
  private int flightNumber;
  private ICountry country;
  private ICity city;
  private IDate dateFlight;
  @Setter private String status;
  private String airline;
  @Setter private IAircraft aircraft;

  @Override
  public String toString() {

    var leftAlignFormat =
        "| %-5s | %-9s | %-11s | %-15s | %-12s | %-16s | %-20s | %-17s | %-16s | %-8s |";

    return String.format(
        leftAlignFormat,
        flightNumber,
        airline,
        aircraft.getModel(),
        country.getOriginCountry(),
        city.getOriginCity(),
        dateFlight.getDates().get(0),
        country.getDestinationCountry(),
        city.getDestinationCity(),
        dateFlight.getDates().get(1),
        status);
  }
}
