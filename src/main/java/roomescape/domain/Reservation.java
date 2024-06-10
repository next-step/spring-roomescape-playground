package roomescape.domain;

import roomescape.exception.NotFoundReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation {
    private int id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(String name, String date, String time) {
        validateEmpty(name);
        validateDate(date);
        validateTime(time);

        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(int id, String name, String date, String time) {
        validateEmpty(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Reservation toEntity(int id, Reservation reservation) {
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
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 맞지 않습니다.");
        }
    }

}
