package roomescape.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.CreateInfoReservationDto;
import roomescape.application.dto.CreateReservationDto;
import roomescape.application.exception.ReservationNotFoundException;
import roomescape.application.exception.TimeNotFoundException;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.repository.ReservationRepository;
import roomescape.application.dto.ReadReservationDto;
import roomescape.domain.repository.TimeRepository;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<ReadReservationDto> readAll() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                           .map(ReadReservationDto::from)
                           .toList();
    }

    public CreateInfoReservationDto create(final CreateReservationDto createDto) {
        final Time time = timeRepository.findById(createDto.getTimeId())
                                        .orElseThrow(() -> new TimeNotFoundException("예약 시간이 존재하지 않습니다."));
        final Reservation newReservation = new Reservation(null, createDto.getName(), createDto.getDate(), time);
        final Reservation persistReservation = reservationRepository.save(newReservation);

        return CreateInfoReservationDto.from(persistReservation);
    }

    public void deleteById(final Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("존재하지 않는 예약입니다.");
        }

        reservationRepository.deleteById(id);
    }
}
