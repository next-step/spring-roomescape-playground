package roomescape.dto;

public class TimeResponseDto {
    private Long id;
    private String time;

    public TimeResponseDto(Long id, String time) {
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
