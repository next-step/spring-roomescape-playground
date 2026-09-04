package roomescape.initializer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;
import roomescape.dto.ReservationCreateCommand;
import roomescape.dto.TimeCreateCommand;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReservationInitializer {

    private final ReservationService reservationService;
    private final TimeService timeService;
    private final Clock clock;

    public ReservationInitializer(ReservationService reservationService, TimeService timeService, Clock clock) {
        this.reservationService = reservationService;
        this.timeService = timeService;
        this.clock = clock;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        LocalDate today = LocalDate.now(clock);

        createReservation(today.plusDays(1), LocalTime.of(10, 0));
        createReservation(today.plusDays(2), LocalTime.of(11, 0));
        createReservation(today.plusDays(3), LocalTime.of(12, 0));
    }

    private void createReservation(LocalDate date, LocalTime startAt) {
        Time time = timeService.findAll().stream()
                .filter(savedTime -> savedTime.getStartAt().equals(startAt))
                .findFirst()
                .orElseGet(() -> timeService.createTime(new TimeCreateCommand(startAt)));

        reservationService.createReservation(new ReservationCreateCommand("브라운", date, time.getId()));
    }
}
