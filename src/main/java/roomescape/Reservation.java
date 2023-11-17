package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(){
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time){
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public LocalDate getDate(){
        return date;
    }
    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
    public LocalTime getTimeNotFormatted(){
        return time;
    }

    public static Reservation toEntity(Reservation reservation, Long id){
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
