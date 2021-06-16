package org.kodigo.airport.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kodigo.airport.utilities.AircraftCatalogue;

@Data
@AllArgsConstructor
public class DisplayCatalogueOption implements IOption {
  private String description;

  @Override
  public void execute() {
    String aircraftCatalogueTable = AircraftCatalogue.getInstance().getCatalogue();
    System.out.println("The list of aircraft allowed in the airport: \n" + aircraftCatalogueTable);
  }
}
