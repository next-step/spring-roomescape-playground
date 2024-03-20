package roomescape.reservation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationRequest;
import roomescape.reservation.repository.ReservationRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validateRequestBody(ReservationRequest reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate().isEmpty()|| reservation.getTime().isEmpty())
            throw new IllegalArgumentException("예약 정보가 비어있습니다.");
    }
    public void validateRequestParam(Reservation reservation) {
        if(reservation == null)
            throw new IllegalArgumentException("해당하는 예약 내역이 없습니다.");
    }
    @GetMapping
    public List<Reservation> getReservationList() {
    return reservationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody final ReservationRequest reservation) {
        validateRequestBody(reservation);
        Reservation addedReservation = reservationRepository.save(new Reservation(reservation.getName(), LocalDate.parse(reservation.getDate()), LocalTime.parse(reservation.getTime())));

        return ResponseEntity.created(URI.create("/reservations/" + addedReservation.getId())).body(addedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        Reservation deletedReservation = reservationRepository.findById(id);
        validateRequestParam(deletedReservation);
        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
