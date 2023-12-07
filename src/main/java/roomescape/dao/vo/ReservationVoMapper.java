package roomescape.dao.vo;

import roomescape.domain.Reservation;

public class ReservationVoMapper {

    private ReservationVoMapper() {

    }

    public static ReservationVo domainToVo(Reservation reservation) {
        return new ReservationVo(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTimeId(),
            reservation.getTimeValue()
        );
    }

    public static Reservation voToDomain(ReservationVo reservationVo) {
        return new Reservation(
            reservationVo.getReservation_id(),
            reservationVo.getName(),
            reservationVo.getDate(),
            reservationVo.getTime_id(),
            reservationVo.getTime_value()
        );
    }

}
