package org.kodigo.airport.utilities;

import org.kodigo.airport.Airport;
import org.kodigo.airport.entities.Aircraft;
import org.kodigo.airport.entities.IAircraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AircraftCatalogue implements IAircraftCatalogue {
  private static final Map<String, IAircraft> AIRCRAFT_CATALOGUE = new HashMap<>();

  private static final BufferedReader INPUT = Airport.INPUT;

  private static IAircraftCatalogue catalogue;

  private AircraftCatalogue() {}

  static {
    AIRCRAFT_CATALOGUE.put("Airbus A350", new Aircraft("Airbus A350", 200, 13000));
    AIRCRAFT_CATALOGUE.put("Airbus A320", new Aircraft("Airbus A320", 150, 12000));
    AIRCRAFT_CATALOGUE.put("Airbus A380", new Aircraft("Airbus A380", 180, 14000));
    AIRCRAFT_CATALOGUE.put("Airbus A220", new Aircraft("Airbus A220", 170, 1500));
    AIRCRAFT_CATALOGUE.put("Airbus A321", new Aircraft("Airbus A321", 200, 16000));
    AIRCRAFT_CATALOGUE.put("Boeing 747", new Aircraft("Boeing 747", 140, 11000));
    AIRCRAFT_CATALOGUE.put("Boeing 737", new Aircraft("Boeing 737", 210, 10500));
    AIRCRAFT_CATALOGUE.put("Boeing 777", new Aircraft("Boeing 777", 220, 14500));
    AIRCRAFT_CATALOGUE.put("Boeing 767", new Aircraft("Boeing 767", 190, 15000));
  }

  public static IAircraftCatalogue getInstance() {
    if (catalogue == null) {
      catalogue = new AircraftCatalogue();
    }
    return catalogue;
  }

  @Override
  public IAircraft getAircraftFromCatalogue(String aircraft) throws IOException {
    boolean isValidAircraft;
    do {
      isValidAircraft = isElementInCatalogue(aircraft);
      if (!isValidAircraft) {
        System.out.println(
            "The aircraft is not allowed in airport, select an aircraft from the catalogue");
        System.out.println(getCatalogue());
        aircraft = INPUT.readLine();
      }
    } while (!isValidAircraft);
    return AIRCRAFT_CATALOGUE.get(aircraft);
  }

  @Override
  public boolean isElementInCatalogue(String aircraft) {
    return AIRCRAFT_CATALOGUE.containsKey(aircraft);
  }

  @Override
  public String getCatalogue() {
    var leftAlignFormat = "| %-20s |%n";
    var separator = "+----------------------+\n";
    var table = new StringBuilder(separator);
    table.append(String.format("| Aircraft Model       |%n"));
    table.append(separator);
    List<String> aircraftList = new ArrayList<>(AIRCRAFT_CATALOGUE.keySet());
    aircraftList.sort(String::compareTo);
    for (String aircraft : aircraftList) {
      table.append(String.format(leftAlignFormat, aircraft));
    }
    table.append(separator);

    return table.toString();
  }
}
