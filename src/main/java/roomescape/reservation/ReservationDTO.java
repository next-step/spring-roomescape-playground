package roomescape.reservation;

import jakarta.validation.constraints.NotBlank;

public class ReservationDTO {

    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String date;
    @NotBlank
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
