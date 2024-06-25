package roomescape.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeDomain {
    private Long id;
    private String time;

    public TimeDomain(){}
    public TimeDomain(String time){
        this.time= time;
    }
    public TimeDomain(Long id, String time) {
        this.id = id;
        this.time = time;
    }
}
