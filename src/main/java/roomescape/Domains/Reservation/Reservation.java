package roomescape.Domains.Reservation;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import roomescape.Domains.Time.Time;

@Entity
public class Reservation {

  protected Reservation() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String date;
  @ManyToOne
  @JoinColumn(name = "time_id")
  private Time time;

  public Reservation(final String name, final String date, final Time time) {
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public Time getTime() {
    return time;
  }


  public String getName() {
    return name;
  }

  public String getDate() {
    return date;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
