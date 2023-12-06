package roomescape.domain.time.entity;

import lombok.*;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Time {

    private Long id;
    private LocalTime time;

}
