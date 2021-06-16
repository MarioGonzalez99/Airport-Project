package org.kodigo.airport.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kodigo.airport.flight.FlightManager;

@Data
@AllArgsConstructor
public class UpdateFlightOption implements IOption {
  private String description;

  @Override
  public void execute() {
    FlightManager.getInstance().update();
  }
}
