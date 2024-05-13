package roomescape.dto;

import roomescape.dto.exception.NotFoundReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, String time) {
        validateEmpty(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        validateEmpty(name);
        validateDate(date);
        validateTime(time);

        this.name = name;
        this.date = date;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public static Reservation toEntity(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    private void validateEmpty(String data) {
        if (data.isEmpty()) {
            throw new NotFoundReservationException("값이 존재하지 않습니다.");
        }
    }

    private void validateDate(String dateString) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 맞지 않습니다.");
        }
    }

    private void validateTime(String timeString) {
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

        try {
            LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 맞지 않습니다.");
        }
    }

}
