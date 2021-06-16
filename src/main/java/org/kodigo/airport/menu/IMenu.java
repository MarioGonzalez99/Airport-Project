package org.kodigo.airport.menu;

public interface IMenu {

  void displayOptions();

  IOption getOption();

  void executeOption(IOption option);
}
