package roomescape.entity.Dto;

public class TimeInDto {

    private String time;

    private TimeInDto() {
        this.time = null;
    }

    public TimeInDto(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
