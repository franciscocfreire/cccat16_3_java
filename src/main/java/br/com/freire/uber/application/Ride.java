package br.com.freire.uber.application;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class Ride {
    private UUID rideId;
    private String passagerId;
    private double fromLat;
    private double fromLong;
    private double toLat;
    private double toLong;
    private String status;
    private LocalDateTime date;

}
