package roomescape.reservation;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.sql.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  private String name;

  private String date;

  private Time time;

  @Builder
  private Reservation(Long id, String name, String date, Time time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

}
