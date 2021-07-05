package org.kodigo.airport.menu;

import org.kodigo.airport.Airport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirportMenu implements IMenu {
  List<IOption> options = new ArrayList<>();

  public AirportMenu() {
    options.add(new DisplayFlightOption("Display current flights"));
    options.add(new AddFlightOption("Add new flight"));
    options.add(new UpdateFlightOption("Update flight status"));
    options.add(new DisplayCatalogueOption("Aircraft Catalogue"));
    options.add(new GenerateAndSendReportOption("Generate and Send Report"));
    options.add(new ExitOption("Exit program"));
  }

  @Override
  public void displayOptions() {
    var optionsMsg = new StringBuilder("************-Menu-Options-************\n");
    for (IOption c : options) {
      optionsMsg
          .append(options.indexOf(c) + 1)
          .append(". ")
          .append(c.getDescription())
          .append("\n");
    }
    optionsMsg.append("**************************************");
    System.out.println(optionsMsg);
  }

  @Override
  public IOption getOption() {
    int optionNum;
    try {
      optionNum = Integer.parseInt(Airport.INPUT.readLine()) - 1;
    } catch (NumberFormatException e) {
      optionNum = -1;
      System.out.println("Didn't enter a valid number");
    } catch (IOException e) {
      optionNum = -1;
      System.out.println("There was an IOException");
    }
    if (optionNum < 0 || optionNum > options.size() - 1)
      return new DefaultOption("No valid choice selected");
    return options.get(optionNum);
  }

  @Override
  public void executeOption(IOption option) {
    option.execute();
  }
}
