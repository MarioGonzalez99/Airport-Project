package org.kodigo.airport.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlightDetails implements IFlightDetails {
    private String status;
    private String airline;
    private IAircraft aircraft;
}
