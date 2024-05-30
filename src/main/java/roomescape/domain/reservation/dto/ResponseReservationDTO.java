package roomescape.domain.reservation.dto;

public record ResponseReservationDTO(long id,
                                     String name,
                                     String date,
                                     String time) {
    public static ResponseReservationDTO toResponseReservation(ReservationDTO reservation) {
        return new ResponseReservationDTO(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time().getTime().toString()
        );
    }
}
