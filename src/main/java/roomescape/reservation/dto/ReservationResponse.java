package roomescape.reservation.dto;

import lombok.Data;
import roomescape.reservation.db.ReservationEntity;
import roomescape.time.db.TimeEntity;

@Data
public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private TimeEntity timeEntity;

    public static ReservationResponse from(ReservationEntity reservationEntity){

        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservationEntity.getId());
        dto.setName(reservationEntity.getName());
        dto.setDate(reservationEntity.getDate());
        dto.setTimeEntity(reservationEntity.getTimeEntity());
        return dto;
    }

}
