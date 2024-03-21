package roomescape.dto;

public class ReadReservationDto {
    private final String name;
    private final String date;
    private final Long timeId;

    public ReadReservationDto(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public Long getTimeId() {
        return this.timeId;
    }

}
