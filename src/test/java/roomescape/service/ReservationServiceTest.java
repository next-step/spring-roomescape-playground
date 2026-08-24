package roomescape.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void 예약_목록을_정상적으로_반환한다() {
        // Given
        List<Reservation> reservations = List.of(
                new Reservation(1, "이준환", LocalDate.of(2026, 8, 20), LocalTime.of(10, 0))
        );
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        List<Reservation> result = reservationService.getReservations();

        // Then
        assertEquals(reservations, result);
    }

    @Test
    void 예약을_생성하면_생성된_예약을_반환한다() {
        // Given
        Reservation savedReservation = new Reservation(1, "이준환", LocalDate.of(2026, 8, 20), LocalTime.of(10, 0));

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(savedReservation);

        // When
        Reservation result = reservationService.createReservation(
                "이준환",
                LocalDate.of(2026, 8, 20),
                LocalTime.of(10, 0)
        );

        // Then
        assertEquals(savedReservation, result);
    }

    @Test
    void 예약을_삭제하면_정상적으로_종료된다() {
        // Given
        long id = 1;
        when(reservationRepository.deleteById(id)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> reservationService.deleteReservation(id));
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외가_발생한다() {
        // Given
        long id = 999;
        when(reservationRepository.deleteById(id)).thenReturn(false);

        // When & Then
        assertThrows(
                NotFoundReservationException.class,
                () -> reservationService.deleteReservation(id)
        );
    }
}
