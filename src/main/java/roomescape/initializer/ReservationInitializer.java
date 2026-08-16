package roomescape.initializer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationCreateCommand;
import roomescape.service.ReservationService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReservationInitializer {

    private final ReservationService reservationService;
    private final Clock clock;

    public ReservationInitializer(ReservationService reservationService, Clock clock) {
        this.reservationService = reservationService;
        this.clock = clock;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        LocalDate today = LocalDate.now(clock);

        addReservation(today.plusDays(1), LocalTime.of(10, 0));
        addReservation(today.plusDays(2), LocalTime.of(11, 0));
        addReservation(today.plusDays(3), LocalTime.of(12, 0));
    }

    private void addReservation(LocalDate date, LocalTime time) {
        reservationService.addReservation(new ReservationCreateCommand("브라운", date, time));
    }
}
