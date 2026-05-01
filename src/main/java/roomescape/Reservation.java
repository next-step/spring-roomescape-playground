package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.NullValueInNestedPathException;

public class Reservation {

    private int reservationId;
    private String nameOfUser;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(int id, String name, LocalDate date, LocalTime time) {
        checkValidId(id);
        checkValidName(name);
        checkValidDate(date);
        checkValidTime(time);
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

    public boolean isSameTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
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

    private void checkValidDate(LocalDate date){
        if (date == null){
            throw new IllegalArgumentException("날짜가 비었습니다.");
        }

        LocalDate now = LocalDate.now();
        if (date.isBefore(now)){
            throw new IllegalArgumentException("이미 지난 날짜입니다.");
        }
    }

    private void checkValidTime(LocalTime time){
        if (time == null){
            throw new IllegalArgumentException("시간이 비었습니다.");
        }

        LocalTime now = LocalTime.now();
        if (time.isBefore(now)){
            throw new IllegalArgumentException("이미 지난 시간입니다.");
        }
    }
}
