package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationRowMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class ReservationDAOTest {
    private ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO = new ReservationDAO(jdbcTemplate, new ReservationRowMapper());
    }

    @Test
    @DisplayName("예약이 DB에 저장이 잘 되는지 확인")
    void 예약을_생성할_수_있다() {
        //given
        LocalTime fixTime = LocalTime.now();
        ReservationCreateRequest request = new ReservationCreateRequest("파도", LocalDate.now().plusDays(1), fixTime);

        //when
        ReservationResponse response = reservationDAO.createReservation(request);

        //then
        assertAll(
            () -> assertThat(response.id()).isNotNull(),
            () -> assertThat(response.name()).isEqualTo("파도"),
            () -> assertThat(response.date()).isEqualTo(LocalDate.now().plusDays(1)),
            () -> assertThat(response.time()).isEqualTo(fixTime)
        );
    }

    @Test
    @DisplayName("저장된 예약들을 제대로 조회하는지 확인")
    void 예약을_조회할_수_있다() {
        //given
        ReservationCreateRequest request1 = new ReservationCreateRequest("콜리", LocalDate.now().plusDays(1), LocalTime.now());
        ReservationCreateRequest request2 = new ReservationCreateRequest("파도", LocalDate.now().plusDays(3), LocalTime.now());
        ReservationCreateRequest request3 = new ReservationCreateRequest("커찬", LocalDate.now().plusDays(2), LocalTime.now());
        reservationDAO.createReservation(request1);
        reservationDAO.createReservation(request2);
        reservationDAO.createReservation(request3);

        //when
        List<Reservation> reservations = reservationDAO.findReservations();
        Set<String> names = reservations.stream().map(Reservation::getName).collect(Collectors.toSet());

        //then
        assertAll(
            () -> assertThat(reservations).hasSize(3),
            () -> assertThat(names).isEqualTo(Set.of("콜리", "파도", "커찬"))
        );
    }

    @Test
    @DisplayName("예약을 잘 삭제할 수 있는지 확인")
    void 예약을_삭제할_수_있다() {
        //given
        ReservationCreateRequest request1 = new ReservationCreateRequest("파도", LocalDate.now().plusDays(1), LocalTime.now());
        ReservationCreateRequest request2 = new ReservationCreateRequest("콜리", LocalDate.now().plusDays(1), LocalTime.now());
        ReservationResponse response1 = reservationDAO.createReservation(request1);
        ReservationResponse response2 = reservationDAO.createReservation(request2);

        //when
        reservationDAO.deleteReservation(response1.id());
        List<Reservation> reservations = reservationDAO.findReservations();

        //then
        assertAll(
            () -> assertThat(reservations).hasSize(1),
            () -> assertThat(reservations.get(0).getName()).isEqualTo("콜리")
        );
    }
}
