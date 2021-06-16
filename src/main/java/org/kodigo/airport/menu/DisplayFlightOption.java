package org.kodigo.airport.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kodigo.airport.flight.FlightManager;
import org.kodigo.airport.flight.IManager;

@Data
@AllArgsConstructor
public class DisplayFlightOption implements IOption {
  private String description;

  @Override
  public void execute() {
    IManager flightManager = FlightManager.getInstance();
    flightManager.read();
  }
}
