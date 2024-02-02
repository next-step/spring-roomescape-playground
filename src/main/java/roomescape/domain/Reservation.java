package roomescape.domain;

import java.time.LocalDate;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        validate(name, time);
    }

    private void validate(String name, ReservationTime time) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 에약자, 날짜가 모두 필요합니다.");
        }
        if (time == null || time.getId() == null) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 존재하지 않는 예약시간입니다.");
        }
    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this(id, name, LocalDate.parse(date), time);
    }

    public Reservation(String name, String date, ReservationTime time) {
        this(null, name, date, time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Long getTimeId() {
        return time.getId();
    }
}
