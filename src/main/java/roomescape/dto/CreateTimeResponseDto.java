package roomescape.dto;

public class CreateTimeResponseDto {

    private final Long timeId;
    private final String time;

    public CreateTimeResponseDto(Long timeId, String time) {
        this.timeId = timeId;
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
    public Long getTimeId() {
        return this.timeId;
    }

}
