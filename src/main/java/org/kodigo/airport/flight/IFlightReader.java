package org.kodigo.airport.flight;

import org.kodigo.airport.entities.IFlight;

import java.util.Map;

public interface IFlightReader {
    void readFlights(Map<Integer, IFlight> flights);
}
