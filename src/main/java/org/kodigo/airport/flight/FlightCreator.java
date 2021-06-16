package org.kodigo.airport.flight;

import org.kodigo.airport.Airport;
import org.kodigo.airport.entities.*;
import org.kodigo.airport.utilities.*;

import java.io.BufferedReader;
import java.io.IOException;

public class FlightCreator implements IFlightCreator {
    private static final BufferedReader INPUT = Airport.INPUT;

    @Override
    public void createFlight(){
        var msgOption = """
                How do you want to add the new flight?
                1. Add a single flight using keyboard
                2. Add a set of flights using an excel sheet""";
        System.out.println(msgOption);
        var optionNum = 0;
        try {
            optionNum = Integer.parseInt(INPUT.readLine());
        } catch (IOException e) {
            System.out.println("Not a valid input");
        }
        if(optionNum == 1){
            this.createFlightUsingKeyboard();
        }else if(optionNum == 2){
            this.createFlightUsingBatch();
        } else {
            System.out.println("Didn't select a valid option");
        }
    }

    @Override
    public void addFlight(int flightNumber, String airline, IAircraft aircraft, String originCountry, String originCity, String destinationCountry, String destinationCity, String departureDate, String arrivalDate, String status) {
        IFlight flight = new Flight(flightNumber, new Country(originCountry, destinationCountry),
                new City(originCity, destinationCity), new Date(departureDate, arrivalDate),
                status, airline, aircraft);

        FlightManager.getInstance().getFlights().put(flightNumber, flight);
    }

    private void createFlightUsingKeyboard(){
        try {
            System.out.println("Enter the flight number");
            var flightNumber = Integer.parseInt(INPUT.readLine());
            System.out.println("Enter the airline");
            String airline = INPUT.readLine();

            System.out.println("Enter the aircraft:");
            IAircraft aircraft = AircraftCatalogue.getInstance().getAircraftFromCatalogue(INPUT.readLine());

            System.out.println("Enter the origin country");
            String originCountry = INPUT.readLine();
            System.out.println("Enter the origin city");
            String originCity = INPUT.readLine();
            System.out.println("Enter the destination country");
            String destinationCountry = INPUT.readLine();
            System.out.println("Enter the destination city");
            String destinationCity = INPUT.readLine();

            IDateTime dateTime = new DateTime();
            System.out.println("Enter the departure date and time (dd/MM/yyyy HH:mm)");
            String departureDate = dateTime.getDateTime();

            System.out.println("Enter the arrival date and time (dd/MM/yyyy HH:mm)");
            String arrivalDate = dateTime.getDateTime();

            var status = Status.ON_TIME.toString();

            addFlight(flightNumber, airline, aircraft, originCountry, originCity,
                    destinationCountry, destinationCity, departureDate, arrivalDate, status);

        } catch (NumberFormatException e) {
            System.out.println("No valid number entered");
        } catch (IOException e) {
            System.out.println("There was a problem when adding a new flight");
        }
    }


    private void createFlightUsingBatch() {
        IFileManager flightExcel = new FlightExcelFile();
        flightExcel.readFile();
    }

}
