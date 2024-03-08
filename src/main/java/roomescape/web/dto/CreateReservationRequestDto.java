package roomescape.web.dto;

public class CreateReservationRequestDto {

    private String name;
    private String date;
    private Long timeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && date != null && !date.isEmpty()
                && timeId != null;
    }
}


