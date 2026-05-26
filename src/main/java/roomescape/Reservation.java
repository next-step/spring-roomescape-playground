package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Time getTime() { return time; }

    public static class Request {
        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private Long timeId;

        public Request() {}
        public String getName() { return name; }
        public LocalDate getDate() { return date; }
        public Long getTimeId() { return timeId; }
    }

    public static class Response {
        private Long id;
        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private Time time;

        public Response(Reservation reservation) {
            this.id = reservation.getId();
            this.name = reservation.getName();
            this.date = reservation.getDate();
            this.time = reservation.getTime();
        }
        public Long getId() { return id; }
        public String getName() { return name; }
        public LocalDate getDate() { return date; }
        public Time getTime() { return time; }
    }
}
