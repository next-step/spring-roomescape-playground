package roomescape.reservation;

public class ReservationDTO {
    private long id;
    private String name;
    private String date;
    private String time;

    public ReservationDTO(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

}
