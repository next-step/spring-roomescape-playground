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

  public void update(Reservation newReservation){
    this.id = newReservation.id;
    this.name = newReservation.name;
    this.date = newReservation.date;
    this.time = newReservation.time;
  }
}
