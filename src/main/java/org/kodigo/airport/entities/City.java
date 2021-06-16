package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City implements ICity {
  private String originCity;
  private String destinationCity;
}
