package roomescape.domain.reservationTime.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.domain.reservationTime.dto.ReservationTimeRequest;
import roomescape.domain.reservationTime.dto.ReservationTimeResponse;
import roomescape.domain.reservationTime.repository.ReservationTimeRepository;
import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.global.exception.RoomescapeServerException;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest timeRequest) {
        ReservationTime newReservationTime = reservationTimeRepository.create(timeRequest.toReservationTime());

        return ReservationTimeResponse.fromReservationTime(newReservationTime);
    }

    public List<ReservationTimeResponse> getReservationTimes() {

        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::fromReservationTime)
                .toList();
    }

    @Transactional
    public void deleteReservationTime(final long id) {
        if (id < 0) {
            throw new RoomescapeBadRequestException("잘못된 아이디 입니다.");
        }

        reservationTimeRepository.remove(id);
    }
}
