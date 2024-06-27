package br.com.freire.uber.resource;

import br.com.freire.uber.application.Ride;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RideDAODatabase implements RideDAO {

    private final JdbcTemplate jdbcTemplate;

    public RideDAODatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRide(Ride ride) {
        jdbcTemplate.update("INSERT INTO cccat16.ride (ride_id, passenger_id, from_lat, from_long, to_lat, to_long, status, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                ride.getRideId(), ride.getPassengerId(), ride.getFromLat(), ride.getFromLong(), ride.getToLat(), ride.getToLong(), ride.getStatus(), ride.getDate());
    }

    @Override
    public Optional<Ride> getRideById(String rideId) {
        String sql = "SELECT * FROM cccat16.ride WHERE ride_id = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, UUID.fromString(rideId));
            Ride ride = convertMapToRide(result);
            return Optional.of(ride);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean hasActiveRideByPassengerId(String passengerId) {
        String sql = "SELECT * FROM cccat16.ride WHERE passenger_id = ? and status <> 'completed'";
        try {
            jdbcTemplate.queryForMap(sql, UUID.fromString(passengerId));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

    private Ride convertMapToRide(Map<String, Object> result) {
        if (result == null) return null;
        Ride ride = new Ride();
        ride.setRideId(((UUID) result.get("ride_id")));
        ride.setPassengerId((UUID) result.get("passenger_id"));
        ride.setStatus((String) result.get("status"));
        ride.setFromLat(((BigDecimal) result.get("from_lat")));
        ride.setFromLong(((BigDecimal) result.get("from_long")));
        ride.setToLat(((BigDecimal) result.get("to_lat")));
        ride.setToLong(((BigDecimal) result.get("to_long")));
        Timestamp timestamp = (Timestamp) result.get("date");
        if (timestamp != null) {
            ride.setDate(timestamp.toLocalDateTime());
        }
        return ride;


    }
}
