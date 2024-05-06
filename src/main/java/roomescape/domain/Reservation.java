package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import roomescape.dto.TimeDto;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;
}
