package roomescape.domain;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Time {

    private final Long id;

    private final LocalTime time;
}
