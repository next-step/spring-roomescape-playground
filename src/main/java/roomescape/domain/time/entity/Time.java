package roomescape.domain.time.entity;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class Time {

    private Long id;
    private LocalTime time;

    @Builder
    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
