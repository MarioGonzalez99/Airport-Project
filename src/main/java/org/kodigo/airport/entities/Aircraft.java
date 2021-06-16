package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Aircraft implements IAircraft {
  private String model;
  private int passengerCapacity;
  private int distanceRangeKM;

  @Override
  public String toString() {
    return String.format("%s %d(Passengers) %d(KM)", model, passengerCapacity, distanceRangeKM);
  }
}
