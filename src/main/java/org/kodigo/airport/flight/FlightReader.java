package org.kodigo.airport.flight;

import org.kodigo.airport.entities.IFlight;

import java.util.Map;

public class FlightReader implements IFlightReader {
  private static final String LEFT_ALIGN_FORMAT =
      "| %-5s | %-9s | %-11s | %-15s | %-12s | %-16s | %-20s | %-17s | %-16s | %-8s |";

  private final String header =
      String.format(
          LEFT_ALIGN_FORMAT,
          "No.",
          "Airline",
          "Aircraft",
          "Origin Country",
          "Origin City",
          "Departure Date",
          "Destination Country",
          "Destination City",
          "Arrival Date",
          "Status");

  public void readFlights(Map<Integer, IFlight> flights) {
    String margin =
        "--------------------------------------------------------------------------------"
            + "--------------------------------------------------------------------------------";
    System.out.println(margin);
    System.out.println(header);
    System.out.println(margin);
    flights.forEach((flightNumber, flight) -> System.out.println(flight));
    System.out.println(margin);
  }
}
