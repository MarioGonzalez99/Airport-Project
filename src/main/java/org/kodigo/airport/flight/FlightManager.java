package org.kodigo.airport.flight;

import lombok.Getter;
import org.kodigo.airport.entities.*;

import java.util.HashMap;
import java.util.Map;

public class FlightManager implements IManager {
  @Getter private final Map<Integer, IFlight> flights = new HashMap<>();
  @Getter private final Map<IFlight, String> cancellationReport = new HashMap<>();
  @Getter private final Map<IFlight, String> incidentsReport = new HashMap<>();

  private static FlightManager flightManager;

  private FlightManager() {}

  /* We use the singleton pattern to retrieve always the same instance of the class */
  public static FlightManager getInstance() {
    if (flightManager == null) {
      flightManager = new FlightManager();
    }
    return flightManager;
  }

  @Override
  public void create() {
    IFlightCreator flightCreator = new FlightCreator();
    flightCreator.createFlight();
  }

  @Override
  public void read() {
    String margin =
        "--------------------------------------------------------------------------------"
            + "--------------------------------------------------------------------------------";
    var leftAlignFormat =
        "| %-5s | %-9s | %-11s | %-15s | %-12s | %-16s | %-20s | %-17s | %-16s | %-8s |";
    var header =
        String.format(
            leftAlignFormat,
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
    System.out.println(margin);
    System.out.println(header);
    System.out.println(margin);
    flights.forEach((flightNumber, flight) -> System.out.println(flight));
    System.out.println(margin);
  }

  @Override
  public void update() {
    IFlightUpdate flightUpdate = new FlightUpdate();
    flightUpdate.updateFlight();
  }
}
