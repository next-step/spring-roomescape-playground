package roomescape.dto;

import roomescape.domain.Time;

public record TimeResponseDto(Long id, String time) {
    public static TimeResponseDto from (Time time){
        return new TimeResponseDto(
                time.getId(),
                time.getTime());
    }
}
