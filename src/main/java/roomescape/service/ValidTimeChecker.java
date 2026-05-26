package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class ValidTimeChecker {

    public void checkDuplicateException(LocalDate requestDate, LocalTime requestTime,
                                        List<LocalDate> reservationDates, List<LocalTime> reservationTimes) {
        LocalDateTime requestDateTime = LocalDateTime.of(requestDate, requestTime);
        List<LocalDateTime> reservationDateTimes = IntStream.range(0, reservationDates.size())
                .mapToObj(i -> LocalDateTime.of(reservationDates.get(i), reservationTimes.get(i)))
                .toList();
        boolean isDuplicate = reservationDateTimes.stream()
                .anyMatch(reservationTime -> reservationTime.isEqual(requestDateTime));

        if (isDuplicate) {
            throw new ReservationException.DuplicateTimeException();
        }
    }
}
