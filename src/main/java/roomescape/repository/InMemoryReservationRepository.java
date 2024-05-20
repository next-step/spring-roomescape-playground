package roomescape.repository;

import roomescape.dto.ReservationDTO;
import roomescape.exception.ReservationNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, ReservationDTO> reservations = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationDTO> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public Optional<ReservationDTO> findById(Long id) {
        ReservationDTO reservation = reservations.get(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        return Optional.of(reservation);
    }

    @Override
    public ReservationDTO save(ReservationDTO reservation) {
        long id = index.getAndIncrement();
        ReservationDTO newReservation = new ReservationDTO(id, reservation.name(), reservation.date(), reservation.time());
        reservations.put(id, newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
