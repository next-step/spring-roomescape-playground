package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationDTO;
import roomescape.dto.TimeDTO;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    @Autowired
    private ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDTO>> read() {
        List<Reservation> reservations = reservationDao.getAllReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(reservationDTOs);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation newReservation = reservationDao.insertReservation(reservation);
        ReservationDTO newReservationDTO = convertToDTO(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservationDTO.getId())).body(newReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int removed = reservationDao.deleteReservation(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    // Helper method to convert Reservation to ReservationDTO
    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new TimeDTO(reservation.getTime().getId(), reservation.getTime().getTime())
        );
    }

    // Helper method to convert ReservationDTO to Reservation
    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return new Reservation(
                reservationDTO.getId(),
                reservationDTO.getName(),
                reservationDTO.getDate(),
                new Time(reservationDTO.getTime().getId(), reservationDTO.getTime().getTime())
        );
    }
}
