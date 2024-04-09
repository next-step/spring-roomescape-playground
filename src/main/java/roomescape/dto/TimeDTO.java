package roomescape.dto;

import roomescape.entity.Time;

public class TimeDTO {

    private String time;

    public TimeDTO () {

    }

    public TimeDTO(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public static Time toEntity(TimeDTO timeDTO, Long id) {
        return new Time(id, timeDTO.getTime());
    }

    @Override
    public String toString() {
        return "TimeDTO{" +
                "time='" + time + '\'' +
                '}';
    }

}