package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(ReservationDto reservationDto) {
        Reservation newReservation = Reservation.builder().
                id(reservationRepository.index.getAndIncrement()).
                name(reservationDto.getName()).
                date(reservationDto.getDate()).
                time(reservationDto.getTime()).build();

        return reservationRepository.createReservation(newReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findAll().stream()
                .filter(existId -> Objects.equals(existId.getId(), id))
                .findFirst()
                .orElseThrow(()->new NotFoundReservationException("예약 정보를 찾을 수 없습니다."));

        reservationRepository.deleteReservation(reservation);
    }
}
