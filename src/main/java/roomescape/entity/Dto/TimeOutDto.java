package roomescape.entity.Dto;

public class TimeOutDto {

    private Long id;
    private String time;

    public TimeOutDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

}
