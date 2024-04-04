package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Reservation {

    private final Long id;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @NotNull(message = "날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @NotNull(message = "시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;
}
