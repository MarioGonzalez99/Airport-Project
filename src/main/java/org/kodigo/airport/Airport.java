package org.kodigo.airport;

import org.kodigo.airport.menu.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Airport {
  public static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    IMenu menu = new AirportMenu();
    IOption option;
    do {
      menu.displayOptions();
      option = menu.getOption();
      menu.executeOption(option);
    } while (!option.getClass().getName().contains("ExitOption"));

    try {
      INPUT.close();
    } catch (IOException e) {
      System.out.println("An error occurred when trying to close input");
    }
  }
}
