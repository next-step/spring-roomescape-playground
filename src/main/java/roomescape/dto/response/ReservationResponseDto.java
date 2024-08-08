package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.service.ReservationDto;

public class ReservationResponseDto {

  private Long id;

  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime time;

  public ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public static ReservationResponseDto from(final ReservationDto reservationDto) {
    return new ReservationResponseDto(
        reservationDto.getId(),
        reservationDto.getName(),
        reservationDto.getDate(),
        reservationDto.getTime()
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
