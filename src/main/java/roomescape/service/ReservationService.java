package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.entity.Dto.ReservationInDto;
import roomescape.entity.Dto.TimeInDto;
import roomescape.entity.Reservation;
import roomescape.entity.repository.ReservationRepository;
import roomescape.entity.repository.TimeRepository;
import roomescape.entity.value.Time;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public Reservation saveReservation(ReservationInDto reservationInDto) {
        final Time time = findTimeById(reservationInDto.getTimeId());

        final Long savedId = reservationRepository.save(reservationInDto);
        return new Reservation(savedId, reservationInDto.getName(), reservationInDto.getDate(), time);
    }

    private Time findTimeById(Long timeId) {
        return timeRepository.findById(timeId)
            .orElseThrow(() -> new NotFoundException("해당 id를 가진 Time 객체를 찾을 수 없습니다."));
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll().stream()
            .map(reservationOutDto -> {
                Time time = findTimeById(reservationOutDto.getTimeId());
                return Reservation.of(reservationOutDto, time);
            })
            .collect(Collectors.toList());
    }

    public void deleteReservationById(Long id) {
        final int countOfDeleted = reservationRepository.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 예약을 찾을 수 없습니다.");
        }
    }

    public List<Time> findAllTimes() {
        return timeRepository.findAll();
    }

    public Time saveTime(TimeInDto timeInDto) {
        return timeRepository.save(timeInDto);
    }

    public void deleteTimeById(Long id) {
        final int countOfDeleted = timeRepository.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 Time 객체를 찾을 수 없습니다.");
        }
    }

}
