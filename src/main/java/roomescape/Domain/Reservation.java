package roomescape.Domain;

import roomescape.Domain.Time;

public class Reservation {
  private Long id;
  private String name;
  private String date;
  private Time time;

  public Reservation(Long id, String name, String date, Time time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public String getDate() {
    return this.date;
  }
  public Time getTime() {
    return this.time;
  }
}
