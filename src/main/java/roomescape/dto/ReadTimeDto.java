package roomescape.dto;

public class ReadTimeDto {
    private final Long timeId;
    private final String time;

    public ReadTimeDto(Long timeId, String time) {
        this.timeId = timeId;
        this.time = time;
    }

    public Long getTimeId() {
        return this.timeId;
    }

    public String getTime() {
        return this.time;
    }

}
