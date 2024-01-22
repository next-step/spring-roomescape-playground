package roomescape.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.dto.ReadReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReadReservationResponse {

    private final Long id;

    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private ReadReservationResponse(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReadReservationResponse from(final ReadReservationDto readReservationDto) {
        return new ReadReservationResponse(
                readReservationDto.getId(),
                readReservationDto.getName(),
                readReservationDto.getDate(),
                readReservationDto.getTime()
        );
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
