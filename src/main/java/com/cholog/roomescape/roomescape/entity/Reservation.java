package com.cholog.roomescape.roomescape.entity;

import com.cholog.roomescape.roomescape.exception.ReservationNotValidException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        try {
            validateCreateReservation(name, date, time);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ReservationNotValidException(e.getMessage());
        }
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateCreateReservation(String name, LocalDate date, LocalTime time) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 문자열일 수 없습니다.");
        }

        Objects.requireNonNull(name, "이름은 비어 있을 수 없습니다.");
        Objects.requireNonNull(date, "날짜는 비어 있을 수 없습니다.");
        Objects.requireNonNull(time, "시각은 비어 있을 수 없습니다.");
    }

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static Reservation withId(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation other)) {
            return false;
        }
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
