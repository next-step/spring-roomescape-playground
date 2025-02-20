package roomescape.entity.dto;

public class ReservationDto {

    private Long id;

    private String name;
    private String date;
    private Long timeId;

    public ReservationDto(Long id, String name, String date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
