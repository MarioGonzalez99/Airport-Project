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
    IFlightReader flightReader = new FlightReader();
    flightReader.readFlights(flights);
  }

  @Override
  public void update() {
    IFlightUpdate flightUpdate = new FlightUpdate();
    flightUpdate.updateFlight();
  }
}
