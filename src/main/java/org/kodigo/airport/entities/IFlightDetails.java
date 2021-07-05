package org.kodigo.airport.entities;

public interface IFlightDetails {

    String getAirline();

    String getStatus();
    void setStatus(String status);

    IAircraft getAircraft();
    void setAircraft(IAircraft aircraft);
}
