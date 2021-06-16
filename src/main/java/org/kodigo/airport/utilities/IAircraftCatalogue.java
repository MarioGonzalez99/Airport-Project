package org.kodigo.airport.utilities;

import org.kodigo.airport.entities.IAircraft;

import java.io.IOException;

public interface IAircraftCatalogue extends ICatalogue {
  IAircraft getAircraftFromCatalogue(String aircraft) throws IOException;
}
