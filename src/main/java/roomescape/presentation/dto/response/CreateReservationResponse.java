package roomescape.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.dto.CreateInfoReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationResponse {

    private final Long id;

    private final String name;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private CreateReservationResponse(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static CreateReservationResponse from(final CreateInfoReservationDto createInfoReservationDto) {
        return new CreateReservationResponse(
                createInfoReservationDto.getId(),
                createInfoReservationDto.getName(),
                createInfoReservationDto.getDate(),
                createInfoReservationDto.getTime()
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
