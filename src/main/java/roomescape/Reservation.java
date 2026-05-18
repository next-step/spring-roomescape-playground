package roomescape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private int reservationId;
    private String nameOfUser;
    @JsonIgnore
    private LocalDateTime dateTime;

    @JsonCreator
    public Reservation(
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time
    ) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        checkValidName(name);
        checkValidDateTime(dateTime);

        this.nameOfUser = name;
        this.dateTime = dateTime;
        this.reservationId = 0;
    }

    public Reservation(int id, String name, LocalDateTime dateTime) {
        this.reservationId = id;
        this.nameOfUser = name;
        this.dateTime = dateTime;
    }

    private static void checkValidName(String nameOfUser) {
        if (nameOfUser == null || nameOfUser.isBlank()) {
            throw new IllegalArgumentException("이름이 비었습니다.");
        }
    }

    private static void checkValidDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("날짜가 비었습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now)) {
            throw new IllegalArgumentException("이미 지난 날짜입니다.");
        }
    }

    @JsonProperty("date")
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    @JsonProperty("time")
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getId() {
        return reservationId;
    }

    public String getName() {
        return nameOfUser;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
