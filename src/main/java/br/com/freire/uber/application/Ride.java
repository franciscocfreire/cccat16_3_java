package br.com.freire.uber.application;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class Ride {
    private UUID rideId;
    private UUID passengerId;
    private BigDecimal fromLat;
    private BigDecimal fromLong;
    private BigDecimal toLat;
    private BigDecimal toLong;
    private String status;
    private LocalDateTime date;

}
