package roomescape.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.ReservationTimeDto;
import roomescape.application.dto.ReservationTimeInfoDto;
import roomescape.domain.ReservationTime;
import roomescape.domain.repository.ReservationTimeRepository;
import roomescape.exception.BaseException;

import java.util.List;

import static roomescape.exception.ErrorCode.NOT_FOUND_RESERVATION;
import static roomescape.exception.ErrorCode.NOT_FOUND_TIME;

@Transactional
@RequiredArgsConstructor
@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional(readOnly = true)
    public List<ReservationTimeInfoDto> findAll() {
        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeInfoDto::from)
                .toList();
    }

    public ReservationTimeInfoDto save(final ReservationTimeDto reservationTimeDto) {
        final ReservationTime newReservationTime = reservationTimeDto.toEntity();
        ReservationTime reservationTime = reservationTimeRepository.save(newReservationTime);
        return ReservationTimeInfoDto.from(reservationTime);
    }

    public int delete(final Long id) {
        if (!reservationTimeRepository.existById(id)) {
            throw new BaseException(HttpStatus.BAD_REQUEST, NOT_FOUND_TIME);
        }
        return reservationTimeRepository.delete(id);
    }
}
