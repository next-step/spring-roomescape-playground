package roomescape.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.application.dto.request.CreateReservationRequestDto;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;
import roomescape.fake.FakeReservationRepository;
import roomescape.fake.FakeTimeRepository;
import roomescape.repository.reservation.interfaces.ReservationRepository;
import roomescape.repository.reservation.interfaces.TimeRepository;

class ReservationServiceTest {

    public static final int ONE_DAY_LATER = 1;
    public static final String RESERVE_REQUESTER = "곰곰";

    private ReservationService reservationService;
    private final ReservationRepository RESERVATION_REPOSITORY = new FakeReservationRepository();
    private final TimeRepository TIME_REPOSITORY = new FakeTimeRepository();
    private Long savedTimeId;

    @BeforeEach
    void init() {
        savedTimeId = getSavedTimeId();
        reservationService = getReservationService();
    }

    @Test
    void 예약_생성_조회_테스트() {
        // given
        Reservation savedReservation = saveReservation();
        // when
        Reservation foundReservation = getByIdOrThrow(savedReservation);
        // then
        assertThat(foundReservation.getName()).isEqualTo(savedReservation.getName());
    }

    @Test
    void 예약_삭제_테스트() {
        // given
        Reservation savedReservation = saveReservation();
        reservationService.deleteReservation(savedReservation.getId());
        // when
        Throwable catchThrowable = catchThrowable(() -> getByIdOrThrow(savedReservation));

        // then
        assertThat(catchThrowable).isInstanceOf(EntityNotFoundException.class);
    }

    private ReservationService getReservationService() {
        return new ReservationService(RESERVATION_REPOSITORY, TIME_REPOSITORY);
    }

    private Long getSavedTimeId() {
        Time time = new Time(null, LocalTime.now());
        return TIME_REPOSITORY.save(time);
    }

    private Reservation getByIdOrThrow(Reservation savedReservation) {
        return RESERVATION_REPOSITORY.findById(savedReservation.getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    private Reservation saveReservation() {
        CreateReservationRequestDto createReservationRequestDto = getCreateReservationRequestDto();
        return reservationService.createReservation(createReservationRequestDto);
    }

    private CreateReservationRequestDto getCreateReservationRequestDto() {
        LocalDate oneDayLater = LocalDate.now().plusDays(ONE_DAY_LATER);
        return new CreateReservationRequestDto(RESERVE_REQUESTER, oneDayLater, savedTimeId);
    }
}
