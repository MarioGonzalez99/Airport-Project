package org.kodigo.airport.flight;

import org.kodigo.airport.Airport;
import org.kodigo.airport.entities.IFlight;
import org.kodigo.airport.utilities.DateTime;
import org.kodigo.airport.utilities.IDateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightFilter implements IFlightFilter {
    private static final BufferedReader INPUT = Airport.INPUT;

    @Override
    public List<IFlight> filterFlight() {
        displayFilterOption();
        List<IFlight> filteredFlights = new ArrayList<> (FlightManager.getInstance().getFlights().values());
        try {
            var numOption = Integer.parseInt(INPUT.readLine());
            filteredFlights = getFlightList(filteredFlights, numOption);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered");
        } catch (IOException e) {
            System.out.println("An error occurred when receiving input from user");
        }
        return filteredFlights;
    }

    @Override
    public Map<IFlight, String> filterDescriptions(Map<IFlight, String> cancellations , List<IFlight> filteredFlights) {
        return cancellations.entrySet().stream().filter(element -> filteredFlights.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public IFlight getFlight() {
        IFlight flight = null;
        var validFlight = false;
        do {
            try {
                System.out.println("Enter the flight number or -1 if you want to exit to the menu");
                var numberFlight = Integer.parseInt(INPUT.readLine());
                if(numberFlight == -1) break;
                flight = FlightManager.getInstance().getFlights().get(numberFlight);
                validFlight = true;
                if (flight == null){
                    validFlight = false;
                    System.out.println("Didn't selected a valid number flight");
                    System.out.println("Please select one of the following flights");
                    FlightManager.getInstance().read();
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number");
            } catch (IOException e) {
                System.out.println("An error occurred when trying to get input from user");
            }
        } while (!validFlight);
        return flight;
    }

    private void displayFilterOption() {
        var msgOption = """
                Generate report by:
                1. Flight Number
                2. Flight Date""";
        System.out.println(msgOption);
    }

    private List<IFlight> getFlightList(List<IFlight> filteredFlights, int numOption) {
        if (numOption == 1) {
            filteredFlights = filterListByNumber();
        } else if (numOption == 2) {
                filteredFlights = filterListByDate(filteredFlights);
        } else {
            System.out.println("No explicit option was selected, all flights selected");
        }
        return filteredFlights;
    }

    private List<IFlight> filterListByNumber() {
        IFlight flight = getFlight();
        List<IFlight> flightList = new ArrayList<>();
        if(flight!=null) flightList.add(flight);
        return flightList;
    }
    private List<IFlight> filterListByDate(List<IFlight> flights) {
        System.out.println("Enter the date (dd/MM/yyyy)");
        IDateTime dateTime = new DateTime();
        String date = dateTime.getDate();
        return flights.stream().filter(iFlight -> iFlight.getDateFlight().getDates().get(0).contains(date))
                .collect(Collectors.toList());
    }

}
