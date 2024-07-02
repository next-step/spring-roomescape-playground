package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationResponse() {};

    public ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }

    public static ReservationResponse toEntity(Long id, ReservationRequest request) {
        return new ReservationResponse(id, request.getName(), request.getDate(), request.getTime());
    }
}
