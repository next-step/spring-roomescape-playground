package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class Time {
    private Long id;
    private LocalTime time;

}
