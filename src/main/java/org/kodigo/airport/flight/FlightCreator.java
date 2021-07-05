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
    public void addFlight(IFlight flight) {
        FlightManager.getInstance().getFlights().put(flight.getFlightNumber(), flight);
    }

    private void createFlightUsingKeyboard(){
        try {
            int flightNumber = getNumericInput("Enter the flight number");
            var airline = getStringInput("Enter the airline");
            IAircraft aircraft = getAircraft();
            var originCountry = getStringInput("Enter the origin country");
            var originCity = getStringInput("Enter the origin city");
            var destinationCountry = getStringInput("Enter the destination country");
            var destinationCity = getStringInput("Enter the destination city");
            IDateTime dateTime = new DateTime();
            var departureDate = getDate(dateTime, "Enter the departure date and time (dd/MM/yyyy HH:mm)");
            var arrivalDate = getDate(dateTime, "Enter the arrival date and time (dd/MM/yyyy HH:mm)");
            var status = Status.ON_TIME.toString();

            IFlightDetails flightDetails = new FlightDetails(status, airline, aircraft);
            ICountry country = new Country(originCountry, destinationCountry);
            ICity city = new City(originCity, destinationCity);
            IDate date = new Date(departureDate, arrivalDate);

            IFlight flight = new Flight(flightNumber, flightDetails, country, city, date);
            addFlight(flight);

        } catch (NumberFormatException e) {
            System.out.println("No valid number entered");
        } catch (IOException e) {
            System.out.println("There was a problem when adding a new flight");
        }
    }

    private String getDate(IDateTime dateTime, String s) {
        System.out.println(s);
        return dateTime.getDateTime();
    }

    private IAircraft getAircraft() throws IOException {
        System.out.println("Enter the aircraft:");
        return AircraftCatalogue.getInstance().getAircraftFromCatalogue(INPUT.readLine());
    }

    private String getStringInput(String s) throws IOException {
        System.out.println(s);
        return INPUT.readLine();
    }

    private int getNumericInput(String s) throws IOException {
        System.out.println(s);
        return Integer.parseInt(INPUT.readLine());
    }


    private void createFlightUsingBatch() {
        IFileManager flightExcel = new FlightExcelFile();
        flightExcel.readFile();
    }

}
