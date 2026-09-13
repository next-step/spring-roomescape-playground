package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final TimeResponseDto time;

    public ReservationResponseDto(Long id, String name, LocalDate date, TimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResponseDto.from(reservation.getTime())
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

    public TimeResponseDto getTime() {
        return time;
    }
}
