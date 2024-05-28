package roomescape.reservation.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.exception.InvalidReservationFormException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(@Qualifier("JdbcReservationRepository")ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponseDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        validateReservation(requestDto);
        Time time = timeRepository.findById(requestDto.time());
        Reservation reservation = new Reservation(0, requestDto.name(), requestDto.date(), time);
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
