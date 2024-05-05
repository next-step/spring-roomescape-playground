package roomescape;

public class CreateReservationRequestDto {
    public String date;
    public String name;
    public String time;

    public CreateReservationRequestDto(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}


