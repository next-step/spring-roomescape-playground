package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validateBasic(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateBasic(name, date, time);

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로는 예약할 수 없습니다.");
        }

        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateBasic(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("필수 예약 정보가 누락되었습니다.");
        }
    }

    public boolean hasSameDateTimeWith(Reservation other) {
        return Objects.equals(this.date, other.getDate()) &&
                Objects.equals(this.time, other.getTime());
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

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

    public static class Response {
        private Long id;
        private String name;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        @JsonFormat(pattern = "HH:mm")
        private LocalTime time;

        public Response(Reservation reservation) {
            this.id = reservation.getId();
            this.name = reservation.getName();
            this.date = reservation.getDate();
            this.time = reservation.getTime();
        }

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
}