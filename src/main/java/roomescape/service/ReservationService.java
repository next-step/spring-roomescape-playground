package roomescape.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.web.dto.ReservationDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class ReservationService {
    // private List<Reservation> reservations = new ArrayList<>();
    private final ReservationRepository repository;
    private final AtomicLong index = new AtomicLong(1);

    @Autowired
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservations = repository.findAll();
        return reservations.stream()
        .map(ReservationDto::new)
        .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto createReservation(String name, String date, String time) {
        Reservation reservation = new Reservation(index.incrementAndGet(), name, date, time);

        Reservation savedReservation = repository.save(reservation);

        return new ReservationDto(savedReservation);
    }

    @Transactional
    public void deleteReservationById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Optional<Reservation> getReservationById(Long id) {
        return repository.findById(id);
    }
}
