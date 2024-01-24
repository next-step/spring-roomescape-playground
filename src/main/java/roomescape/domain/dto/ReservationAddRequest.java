package roomescape.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationAddRequest {
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate date;
    private Long time;

    public ReservationAddRequest(Long id, String name, LocalDate date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = timeId;
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

    public Long getTimeId() {
        return time;
    }
}