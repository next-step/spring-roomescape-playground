package roomescape;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.exception.NotFoundReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.service.ReservationService;

class ReservationServiceTest {

    private ReservationService service;

    @BeforeEach
    void setUp() {
        service = new ReservationService();
    }

    @Test
    @DisplayName("동일한 날짜, 시간에 예약하면 예외 발생")
    void throwExceptionWhenDuplicateReservation() {
        ReservationRequest request = new ReservationRequest("홍길동", LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        service.addReservation(request);

        ReservationRequest duplicateRequest = new ReservationRequest("김철수", LocalDate.now().plusDays(1), LocalTime.of(10, 0));

        assertThrows(IllegalArgumentException.class, () -> service.addReservation(duplicateRequest));
    }

    @Test
    @DisplayName("존재하지 않는 예약 ID로 삭제 시 예외 발생")
    void deleteNonExistingReservationThrows() {
        assertThrows(NotFoundReservationException.class, () -> service.deleteReservation(999L));
    }
}
