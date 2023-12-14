package roomescape.dao.reservation;

import roomescape.domain.Reservation;

public class ReservationResultMapMapper {

    private ReservationResultMapMapper() {

    }

    public static ReservationResultMap toResultMap(Reservation reservation) {
        return new ReservationResultMap(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTimeId(),
            reservation.getTimeValue()
        );
    }

    public static Reservation toDomain(ReservationResultMap reservationResultMap) {
        return new Reservation(
            reservationResultMap.reservationId(),
            reservationResultMap.name(),
            reservationResultMap.date(),
            reservationResultMap.timeId(),
            reservationResultMap.timeValue()
        );
    }

}
