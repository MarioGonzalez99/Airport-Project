package org.kodigo.airport.flight;

import org.kodigo.airport.Airport;
import org.kodigo.airport.entities.IFlight;
import org.kodigo.airport.entities.Status;
import org.kodigo.airport.utilities.DateTime;
import org.kodigo.airport.utilities.IDateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightUpdate implements IFlightUpdate {

    private static final IFlightFilter flightFilter = new FlightFilter();
    private static final BufferedReader INPUT = Airport.INPUT;

    @Override
    public void updateFlight() {
        displayUpdateMsg();
        var optionNum = 0;
        try {
            optionNum = Integer.parseInt(INPUT.readLine());
        } catch (IOException e) {
            System.out.println("Not a valid input");
        }
        if (optionNum == 1) {
            delayedFlight();
        } else if (optionNum == 2) {
            onTimeFlight();
        } else if (optionNum == 3) {
            cancelledFlight();
        } else if (optionNum == 4) {
            landedFlight();
        } else {
            System.out.println("Didn't select a valid option");
        }
    }

    private void displayUpdateMsg() {
        var msgOption = """
                What do you want to update?
                1. Delay flight
                2. On Time Flight
                3. Cancel Flight
                4. Land Flight""";
        System.out.println(msgOption);
    }

    private static void delayedFlight() {
        System.out.println("Delayed FLight");
        IFlight flight = flightFilter.getFlight();
        if(flight == null) return;
        System.out.println(flight);
        changeDate(flight);
        flight.getFlightDetails().setStatus(Status.DELAYED.toString());
        System.out.println(flight);
    }

    private static void onTimeFlight() {
        System.out.println("On Time Flight");
        IFlight flight = flightFilter.getFlight();
        if(flight == null) return;
        System.out.println(flight);
        changeDate(flight);
        flight.getFlightDetails().setStatus(Status.ON_TIME.toString());
        System.out.println(flight);
    }

    private static void cancelledFlight() {
        System.out.println("Cancel Flight");
        IFlight flight = flightFilter.getFlight();
        if(flight == null) return;
        System.out.println(flight);
        System.out.println("Enter a reason for the flight cancellation");
        fillDescriptionMap(FlightManager.getInstance().getCancellationReport(), flight);
        flight.getFlightDetails().setStatus(Status.CANCELLED.toString());
        System.out.println(flight);
    }

    private static void landedFlight() {
        System.out.println("Land Flight");
        IFlight flight = flightFilter.getFlight();
        if(flight == null) return;
        System.out.println(flight);
        System.out.println("Was there any incidents in the flight? If yes, enter a description of it");
        fillDescriptionMap(FlightManager.getInstance().getIncidentsReport(), flight);
        flight.getFlightDetails().setStatus(Status.LANDED.toString());
        System.out.println(flight);
    }

    private static void changeDate(IFlight flight){
        IDateTime dateTime = new DateTime();
        System.out.println("Enter new departure date and time (format: dd/MM/yyyy HH:mm)");
        String departureDate = dateTime.getDateTime();
        System.out.println("Enter new arrival date and time (format: dd/MM/yyyy HH:mm)");
        String arrivalDate = dateTime.getDateTime();
        List<String> dates = new ArrayList<>();
        dates.add(departureDate);
        dates.add(arrivalDate);
        flight.getDateFlight().setDates(dates);
    }

    private static void fillDescriptionMap(Map<IFlight, String> report, IFlight flight){
        String reason = null;
        try {
            reason = INPUT.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred when trying to get input from the user");
        }
        report.put(flight, reason);
    }
}
