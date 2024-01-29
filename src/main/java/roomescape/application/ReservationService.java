package roomescape.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.CreateInfoReservationDto;
import roomescape.application.dto.CreateReservationDto;
import roomescape.application.exception.ReservationNotFoundException;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.application.dto.ReadReservationDto;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<ReadReservationDto> readAll() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                           .map(ReadReservationDto::from)
                           .toList();
    }

    public CreateInfoReservationDto create(final CreateReservationDto createReservationDto) {
        final Reservation newReservation = createReservationDto.toEntity();
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
