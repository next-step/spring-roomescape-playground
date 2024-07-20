package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ReservationValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    private final TimeDAO timeDAO;

    @Autowired
    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationDAO.findAllReservations();
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        ReservationValidator.validate(requestDto);

        Time time = timeDAO.findByTimeValue(requestDto.getTime());
        if (time == null) {
            time = timeDAO.insert(new Time(null, requestDto.getTime()));
        }

        Reservation reservation = new Reservation(null, requestDto.getName(), requestDto.getDate(), time);
        Reservation newReservation = reservationDAO.insert(reservation);

        return new ReservationResponseDto(newReservation.getId(), newReservation.getName(), newReservation.getDate(), newReservation.getTime());
    }

    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }
}
