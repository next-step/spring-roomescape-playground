package roomescape.dto;

import roomescape.domain.TimeEntity;

public record TimeDTO(Long id, String time) {

    public static TimeEntity toEntity(TimeDTO dto) {
        return new TimeEntity(dto.id(), dto.time());
    }
}
