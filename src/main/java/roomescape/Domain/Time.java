package roomescape.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Time {
    private Long id;
    private String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    @Override
    public String toString(){
        return time;
    }
}
