package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation save(String name, LocalDate date, Long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new InvalidReservationException("존재하지 않는 시간입니다. timeId=" + timeId));

        return reservationRepository.save(new Reservation(name, date, time));
    }

    @Transactional
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");

        int deleted = reservationRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다. id=" + id);
        }
    }
}
