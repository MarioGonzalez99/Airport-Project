package org.kodigo.airport.utilities;

import org.kodigo.airport.Airport;

import java.io.BufferedReader;
import java.io.IOException;

public class DateTime implements IDateTime {
  private static final BufferedReader INPUT = Airport.INPUT;

  @Override
  public String getDateTime() {
    var arrivalDate = "";
    boolean validDate;
    try {
      do {
        arrivalDate = INPUT.readLine();
        validDate = this.dateTimeValidator(arrivalDate);
        if (!validDate)
          System.out.println(
              "Invalid date format, please make sure to have entered a valid date (dd/MM/yyyy HH:mm)");
      } while (!validDate);
    } catch (IOException e) {
      System.out.println("There was an input error when trying to get the date and time");
    }
    return arrivalDate;
  }

  @Override
  public String getDate() {
    var arrivalDate = "";
    boolean validDate;
    try {
      do {
        arrivalDate = INPUT.readLine();
        validDate = this.dateValidator(arrivalDate);
        if (!validDate)
          System.out.println(
              "Invalid date format, please make sure to have entered a valid date (dd/MM/yyyy)");
      } while (!validDate);
    } catch (IOException e) {
      System.out.println("There was an input error when trying to get the date");
    }
    return arrivalDate;
  }

  private boolean dateTimeValidator(String dateTime) {
    String regExDateTime =
        "^(29/02/(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
            + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2})) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
            + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2})) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
            + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2})) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    return dateTime.matches(regExDateTime);
  }

  private boolean dateValidator(String dateTime) {
    String regExDateTime =
        "^(29/02/(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
            + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$"
            + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$";
    return dateTime.matches(regExDateTime);
  }
}
