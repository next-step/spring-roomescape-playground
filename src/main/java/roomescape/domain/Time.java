package roomescape.domain;

//public record Time(Long id, String time){}

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Time {
    private Long id;
    private String time;
}
