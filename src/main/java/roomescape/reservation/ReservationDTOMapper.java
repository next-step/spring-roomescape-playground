package roomescape.reservation;


public class ReservationDTOMapper {

    public static Reservation toEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setName(reservationDTO.getName());
        reservation.setDate(reservationDTO.getDate());
        reservation.setTime(reservationDTO.getTime());
        return reservation;
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setName(reservation.getName());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setTime(reservation.getTime());
        return reservationDTO;
    }

}
