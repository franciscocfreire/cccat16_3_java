package br.com.freire.uber.resource;

import br.com.freire.uber.application.Ride;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RideDAODatabase implements RideDAO {

    private final JdbcTemplate jdbcTemplate;

    public RideDAODatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRide(Ride ride) {
        jdbcTemplate.update("INSERT INTO cccat16.ride (ride_id, passenger_id, from_lat, from_long, to_lat, to_long, status, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                ride.getRideId(), ride.getPassagerId(), ride.getFromLat(), ride.getFromLong(), ride.getToLat(), ride.getToLong(), ride.getStatus(), ride.getDate());
    }

/*    @Override
    public boolean hasActiveRideByPassagerId(String passagerId) {
        return false;
    }*/
}
