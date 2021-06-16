package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country implements ICountry {
  private String originCountry;
  private String destinationCountry;
}
