package roomescape.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeResponse {

    private Long id;

    private LocalTime time;
}
