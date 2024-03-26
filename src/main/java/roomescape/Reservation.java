package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() { //기본 생성자
    }

    public Reservation(Long id, String name, String date, String time) { //setter
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

//    public static Reservation create (String name, LocalDate date, LocalTime time) {
//        return new Reservation(null,name,date,time); // this()를 사용해서 코드를 줄였어요.
//    }


    public void setId(long id) {
        this.id = id;
    }
    //getter
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
    public static Reservation toEntity(AtomicLong index, Reservation reservation) {
        return new Reservation(index.incrementAndGet(), reservation.name, reservation.date, reservation.time);
    }
}
