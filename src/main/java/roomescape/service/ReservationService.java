package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationRequest request) {
        Time time = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 시간입니다."));

        Reservation reservation = new Reservation(
                null,
                request.name(),
                request.date(),
                time
        );

        if (reservation.isPast()) {
            throw new BadRequestException("예약 시간은 현재 시각 이후여야 합니다.");
        }

        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new BadRequestException("예약번호가 " + id + "인 예약은 존재하지 않습니다.");
        }
    }
}