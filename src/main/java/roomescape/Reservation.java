package roomescape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private int reservationId;
    private String nameOfUser;
    @JsonIgnore
    private LocalDateTime dateTime;;

    public Reservation() {
    }

    public Reservation(int id, String name, LocalDateTime dateTime) {
        checkValidId(id);
        checkValidName(name);
        checkValidDate(dateTime);
        this.reservationId = id;
        this.nameOfUser = name;
        this.dateTime = dateTime;
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

    public void setId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setName(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public void setDate(LocalDate date) {
        if (this.dateTime == null) this.dateTime = LocalDateTime.of(date, LocalTime.MIN);
        else this.dateTime = LocalDateTime.of(date, this.dateTime.toLocalTime());
    }

    public void setTime(LocalTime time) {
        if (this.dateTime == null) this.dateTime = LocalDateTime.of(LocalDate.MIN, time);
        else this.dateTime = LocalDateTime.of(this.dateTime.toLocalDate(), time);
    }

    public boolean isSameTime(LocalDateTime date) {
        return this.dateTime.equals(date);
    }

    private void checkValidId(int id){
        if (id <= 0){
            throw new IllegalArgumentException("id는 자연수여야 합니다.");
        }
    }

    private void checkValidName(String nameOfUser){
        if (nameOfUser == null || nameOfUser.isBlank()){
            throw new IllegalArgumentException("이름이 비었습니다.");
        }
    }

    private void checkValidDate(LocalDateTime dateTime){
        if (dateTime == null){
            throw new IllegalArgumentException("날짜가 비었습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now)){
            throw new IllegalArgumentException("이미 지난 날짜입니다.");
        }
    }
}
