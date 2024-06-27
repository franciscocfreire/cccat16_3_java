package br.com.freire.uber.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ride {
    private final UUID rideId;
    private final UUID passengerId;
    private final BigDecimal fromLat;
    private final BigDecimal fromLong;
    private final BigDecimal toLat;
    private final BigDecimal toLong;
    private final String status;
    private final LocalDateTime date;

    private Ride(UUID rideId, UUID passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date){
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.status = status;
        this.date = date;
    }

    public static Ride create(UUID passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong){
        UUID rideId =UUID.randomUUID();
        String status = "requested";
        LocalDateTime date = LocalDateTime.now();
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, status, date );
    }

    public static Ride restore(UUID rideId, UUID passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date){
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, status, date );
    }

}
