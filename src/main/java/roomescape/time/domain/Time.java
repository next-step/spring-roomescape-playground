package roomescape.time.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Time {

    private Long id;
    private String time;

    public Time(Long id, String time){
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this.time = time;
    }
}
