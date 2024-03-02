package roomescape.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "date", length = 500, nullable = false)
    private String date;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_id")
    private Time time;

    public Reservation() {

    }

    @Builder
    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}

