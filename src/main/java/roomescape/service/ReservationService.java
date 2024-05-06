package roomescape.service;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.dto.TimeDto;
import roomescape.exception.CustomException;

import java.net.URI;
import java.util.List;

@Service
public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<ReservationDto.ReservationResponse> getReservations() {

        return reservationDAO.getReservations().stream().map(reservation -> {
            return ReservationDto.ReservationResponse.builder().id(reservation.getId()).name(reservation.getName()).date(reservation.getDate()).time(TimeDto.builder()
                    .id(reservation.getTime().getId()).time(reservation.getTime().getTime()).build()).build();
        }).toList();

    }

    public ResponseEntity<ReservationDto.ReservationResponse> postReservation(ReservationDto.ReservationRequest reservationRequest) {
        int id = reservationDAO.postReservation(reservationRequest);

        Reservation reservation = reservationDAO.getReservationById((long) id);
        ReservationDto.ReservationResponse reservationResponse = ReservationDto.ReservationResponse.builder().id(reservation.getId()).name(reservation.getName()).date(reservation.getDate()).time(TimeDto.builder()
                .id(reservation.getTime().getId()).time(reservation.getTime().getTime()).build()).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = URI.create("/reservations/" + id);
        httpHeaders.setLocation(uri);

        return new ResponseEntity<ReservationDto.ReservationResponse>(reservationResponse, httpHeaders, HttpStatus.CREATED);
    }

    public void deleteReservation(Long id) {
        try {
            reservationDAO.getReservationById(id);
            reservationDAO.deleteReservations(id);
        } catch (Exception e) {
            throw new CustomException();
        }
    }
}
