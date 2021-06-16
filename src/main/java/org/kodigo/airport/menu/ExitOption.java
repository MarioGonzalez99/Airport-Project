package org.kodigo.airport.menu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExitOption implements IOption {
  private String description;

  @Override
  public void execute() {
    System.out.println("Closing program");
  }
}
