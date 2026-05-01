package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private int reservationId;
    private String nameOfUser;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(int id, String name, LocalDate date, LocalTime time) {
        checkValidId(id);
        this.reservationId = id;
        this.nameOfUser = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return reservationId;
    }

    public String getName() {
        return nameOfUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setName(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    private void checkValidId(int id){
        if (id <= 0){
            throw new IllegalArgumentException("id는 자연수여야 합니다.");
        }
    }
}
