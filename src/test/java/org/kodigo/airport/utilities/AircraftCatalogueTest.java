package org.kodigo.airport.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kodigo.airport.entities.IAircraft;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AircraftCatalogueTest {

  private IAircraftCatalogue aircraftCatalogue;

  @BeforeEach
  void setUp() {
    aircraftCatalogue = AircraftCatalogue.getInstance();
  }

  @Test
  void canGetValidAircraftFromCatalogue() throws IOException {
    String aircraftStr = "Boeing 777";
    IAircraft aircraft = aircraftCatalogue.getAircraftFromCatalogue(aircraftStr);
    assertEquals(aircraft.getModel(), aircraftStr);
  }

  @Test
  void isValidElementInCatalogue() {
    String aircraftStr = "Boeing 777";
    boolean isInCatalogue = aircraftCatalogue.isElementInCatalogue(aircraftStr);
    assertTrue(isInCatalogue);
  }

  @Test
  void isInvalidElementInCatalogue() {
    String aircraftStr = "Boeing 1077";
    boolean isInCatalogue = aircraftCatalogue.isElementInCatalogue(aircraftStr);
    assertFalse(isInCatalogue);
  }

  @Test
  void canGetCatalogue() {
    String aircraftInCatalogue = "Aircraft Model";
    String actualCatalogue = aircraftCatalogue.getCatalogue();
    assertTrue(actualCatalogue.contains(aircraftInCatalogue) );
  }
}
