package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.ErrorMessage;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        LocalDate date;

        try {
            date = LocalDate.parse(request.date());
        } catch (DateTimeParseException e) {
            throw new BadRequestReservationException(ErrorMessage.INVALID_DATE_TIME_FORMAT.getMessage());
        }

        Time time = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new NotFoundReservationException(ErrorMessage.NOT_FOUND_RESERVATION.getMessage()));

        Reservation reservation = new Reservation(null, request.name(), date, time);
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
