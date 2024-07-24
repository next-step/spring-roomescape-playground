package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationSaveRequestDto;
import roomescape.repository.ReservationDAO;
import roomescape.repository.TimeDAO;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public ReservationResponseDto createReservation(ReservationSaveRequestDto requestDto) {
        if (isReservationArgumentEmpty(requestDto)) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        Reservation reservation = new Reservation(
                requestDto.getName(),
                requestDto.getDate(),
                timeDAO.findById(requestDto.getTime()).get());
        Long id = reservationDAO.insert(reservation, reservation.getTime().getId());

        return new ReservationResponseDto(id, reservation);
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationDAO.findAllReservations()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }

    private boolean isReservationArgumentEmpty(ReservationSaveRequestDto requestDto) {
        return isStringEmpty(requestDto.getName())
                || isStringEmpty(requestDto.getDate()) || (requestDto.getTime() == null);
    }

    private boolean isStringEmpty(String argument) {
        return argument == null || argument.trim().isEmpty();
    }
}
