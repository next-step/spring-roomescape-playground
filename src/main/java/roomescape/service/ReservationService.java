package roomescape.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.InvalidReservationFormException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.InMemoryReservationRepository;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(@Qualifier("JdbcReservationRepository") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

    }

    public List<ReservationResponseDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        validateReservation(requestDto);
        Reservation reservation = new Reservation(0, requestDto.name(), requestDto.date(), requestDto.time());
        Reservation savedReservation = reservationRepository.save(reservation);
        return toResponseDto(savedReservation);
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private void validateReservation(ReservationRequestDto requestDto) {
        if (requestDto.name() == null || requestDto.date() == null || requestDto.time() == null || requestDto.name().isEmpty()) {
            throw new InvalidReservationFormException("필수 정보가 비어있습니다.");
        }
    }

    private ReservationResponseDto toResponseDto(Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
