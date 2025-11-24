package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.ErrorMessage;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        LocalDate date;
        LocalTime time;

        try {
            date = LocalDate.parse(request.date());
            time = LocalTime.parse(request.time());
        } catch (DateTimeParseException e) {
            throw new BadRequestReservationException(ErrorMessage.INVALID_DATE_TIME_FORMAT.getMessage());
        }

        Reservation reservation = new Reservation(null, request.name(), date, time);
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        int deletedCount = reservationRepository.deleteById(id);

        if (deletedCount == 0) {
            throw new NotFoundReservationException(ErrorMessage.NOT_FOUND_RESERVATION.getMessage());
        }
    }
}
