package roomescape.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.ReservationDto;
import roomescape.application.dto.ReservationInfoDto;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.exception.BaseException;

import java.util.List;

import static roomescape.exception.ErrorCode.*;


@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<ReservationInfoDto> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationInfoDto::from)
                .toList();
    }

    public ReservationInfoDto save(final ReservationDto reservationDto) {
        final Reservation newReservation = reservationDto.toEntity();
        Reservation reservation = reservationRepository.save(newReservation);
        return ReservationInfoDto.from(reservation);
    }

    public int delete(Long id) {
        if (!reservationRepository.existById(id)) {
            throw new BaseException(HttpStatus.BAD_REQUEST, NOT_FOUND_RESERVATION);
        }
        return reservationRepository.delete(id);
    }
}
