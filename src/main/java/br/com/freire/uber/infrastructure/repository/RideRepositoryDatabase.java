package br.com.freire.uber.infrastructure.repository;

import br.com.freire.uber.domain.Ride;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RideRepositoryDatabase implements RideRepository {

    private final JdbcTemplate jdbcTemplate;

    public RideRepositoryDatabase(JdbcTemplate jdbcTemplate) {
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
    public boolean hasActiveRideByPassengerId(UUID passengerId) {
        String sql = "SELECT * FROM cccat16.ride WHERE passenger_id = ? and status <> 'completed'";
        try {
            jdbcTemplate.queryForMap(sql, UUID.fromString(passengerId.toString()));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

    private Ride convertMapToRide(Map<String, Object> result) {
        if (result == null) return null;
        Timestamp timestamp = (Timestamp) result.get("date");
        LocalDateTime date = null;
        if (timestamp != null) {
            date = timestamp.toLocalDateTime();
        }

        return Ride.restore(
        (UUID) result.get("ride_id"),
        (UUID) result.get("passenger_id"),
        ((BigDecimal) result.get("from_lat")),
        ((BigDecimal) result.get("from_long")),
        ((BigDecimal) result.get("to_lat")),
        ((BigDecimal) result.get("to_long")),
        (String) result.get("status"),
        date
        );


    }
}
