package roomescape.reservation;


public class ReservationDTOMapper {

    public static Reservation toEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO.getId(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
        return reservation;
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservationDTO;
    }

}
