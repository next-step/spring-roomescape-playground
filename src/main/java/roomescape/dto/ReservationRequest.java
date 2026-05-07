package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationRequest(@NotBlank(message = "이름이 비어 있을 수 없습니다.") String name,
                                 @NotBlank(message = "예약 날짜가 비어 있을 수 없습니다.") String date,
                                 @NotBlank(message = "예약 시간이 비어 있을 수 없습니다.") String time) {
    public Reservation toReservation(Long id) {
        return new Reservation(id, name, toLocalDateTime(date, time));
    }

    private LocalDateTime toLocalDateTime(String date, String time) {
        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
