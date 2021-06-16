package org.kodigo.airport.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Date implements IDate {
  private List<String> dates = new ArrayList<>();

  public Date(String departureDateTime, String arrivalDateTime) {
    dates.add(departureDateTime);
    dates.add(arrivalDateTime);
  }
}
