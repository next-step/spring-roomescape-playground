package roomescape.dao.reservation;

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
            reservationVo.reservationId(),
            reservationVo.name(),
            reservationVo.date(),
            reservationVo.timeId(),
            reservationVo.timeValue()
        );
    }

}
