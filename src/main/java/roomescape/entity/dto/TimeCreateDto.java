package roomescape.entity.dto;

public class TimeCreateDto {

    private String time;

    private TimeCreateDto() {
        this.time = null;
    }

    public TimeCreateDto(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
