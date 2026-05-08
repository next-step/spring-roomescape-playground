package roomescape;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, String time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, String date, String time) {
        if (name == null || name.isBlank() ||
                date == null || date.isBlank() ||
                time == null || time.isBlank()) {
            throw new InvalidReservationException("필수 예약 정보가 누락되었습니다.");
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            if (parsedDate.isBefore(LocalDate.now())) {
                throw new InvalidReservationException("과거 날짜로는 예약할 수 없습니다.");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("날짜 형식이 올바르지 않습니다. (예: 2023-01-01)");
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public static class Response {
        private Long id;
        private String name;
        private String date;
        private String time;

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

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }
}