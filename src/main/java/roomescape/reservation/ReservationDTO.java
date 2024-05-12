package roomescape.reservation;

import jakarta.validation.constraints.NotBlank;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationDTO {

    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String date;
    @NotBlank
    private String time;

    public ReservationDTO(long id, String name, String date, String time) {
        validate(date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String date, String time) {
        validateDate(date);
        validateTime(time);
    }

    private void validateDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            dateFormatter.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validateTime(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            timeFormatter.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
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
