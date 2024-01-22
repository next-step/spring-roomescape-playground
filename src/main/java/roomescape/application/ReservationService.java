package roomescape.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.CreateInfoReservationDto;
import roomescape.application.dto.CreateReservationDto;
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
}
