package roomescape.reservation;


public class ReservationDTOMapper {

    public static Reservation toEntity(ReservationDTO reservationDTO) {
        return new Reservation(reservationDTO.getId(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

}
