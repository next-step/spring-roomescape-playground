package roomescape.entity.dto;

public class ReservationCreateDto {

    private String name;
    private String date;
    private Long time;

    public ReservationCreateDto(String name, String date, Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getTimeId() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
