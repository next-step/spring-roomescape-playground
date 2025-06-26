package roomescape.domain;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
=======
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
>>>>>>> {boya-go}/boya-go
import roomescape.exception.MissingReservationFieldsException;

public class Reservation {

<<<<<<< HEAD
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, date, time);
    }

    @JsonCreator
    public Reservation(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time)
    {
=======
    private static final AtomicLong index = new AtomicLong(1);

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
>>>>>>> {boya-go}/boya-go
        vaildate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void vaildate(String name, LocalDate date, LocalTime time){
        if (name == null || name.isEmpty() || date == null || time == null) {
            throw new MissingReservationFieldsException("모든 필드를 입력하셔야 합니다.");
        }
    }

<<<<<<< HEAD
=======
    public static Reservation create(String name, LocalDate date, LocalTime time) {
        long id = index.getAndIncrement();
        return new Reservation(id, name, date, time);
    }

>>>>>>> {boya-go}/boya-go
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
