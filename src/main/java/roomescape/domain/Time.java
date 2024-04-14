package roomescape.domain;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Time {

    private final Long id;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;
}
