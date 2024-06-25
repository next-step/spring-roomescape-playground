package roomescape.domain;

import roomescape.dto.ReservationDTO;

public record ReservationEntity(Long id, String name, String date, TimeEntity time) {

    public static ReservationDTO toDTO(ReservationEntity entity) {
        return new ReservationDTO(entity.id(), entity.name(), entity.date(), entity.time().id());
    }
}
