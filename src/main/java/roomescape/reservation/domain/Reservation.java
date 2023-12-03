package roomescape.reservation.domain;

import jakarta.persistence.*;
import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.time.domain.Time;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ManyToOne // 다대일(N:1) 관계 설정
    @JoinColumn(name = "time_id") // 외래키 설정
    @Temporal(TemporalType.TIME)
    private Time time;

    @Builder
    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
