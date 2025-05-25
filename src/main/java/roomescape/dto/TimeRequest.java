package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;

@Getter
@RequiredArgsConstructor
public class TimeRequest {

    @NotBlank(message = "시간은 필수입니다.")
    private String time;

    public Time toEntity() {
        return new Time(null, time);
    }
}
