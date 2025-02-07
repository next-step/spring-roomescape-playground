package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;
import roomescape.application.service.converter.ReservationConverter;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationConverter reservationConverter;
    private final ReservationRepository reservationRepository;

    public ReservationService(
            ReservationConverter reservationConverter,
            ReservationRepository reservationRepository
    ) {
        this.reservationConverter = reservationConverter;
        this.reservationRepository = reservationRepository;
    }

    public Reservation findByIdOrThrow(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
    }

    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll().stream().map(reservationConverter::toDto).toList();
    }

    public ReservationResponseDto reserve(CreateReservationRequestDto createReservationRequestDto) {
        Reservation reservationIdNull = reservationConverter.toReservation(createReservationRequestDto);
        Reservation savedReservation = reservationRepository.save(reservationIdNull);
        return reservationConverter.toDto(savedReservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation foundReservation = findByIdOrThrow(reservationId);
        reservationRepository.delete(foundReservation);
    }

}
