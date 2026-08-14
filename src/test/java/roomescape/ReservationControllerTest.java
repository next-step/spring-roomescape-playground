package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.controller.ReservationController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.BadRequestException;
import roomescape.exception.ReservationConflictException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    private static final LocalDate RESERVATION_DATE = LocalDate.of(2027, 8, 15);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void 예약_목록_조회_요청_시_예약_목록을_반환한다() throws Exception {
        // given
        given(reservationService.findAll())
                .willReturn(List.of(
                        new Reservation(1L, "브라운", RESERVATION_DATE, RESERVATION_TIME),
                        new Reservation(2L, "철수", RESERVATION_DATE.plusDays(1), RESERVATION_TIME),
                        new Reservation(3L, "영희", RESERVATION_DATE.plusDays(2), RESERVATION_TIME)
                ));

        // when & then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservations.length()").value(3))
                .andExpect(jsonPath("$.reservations[0].id").value(1))
                .andExpect(jsonPath("$.reservations[0].name").value("브라운"))
                .andExpect(jsonPath("$.reservations[0].date").value(RESERVATION_DATE.toString()))
                .andExpect(jsonPath("$.reservations[0].time").value(RESERVATION_TIME.toString()));

        then(reservationService).should().findAll();
    }

    @Test
    void 예약_목록이_비어있으면_빈_예약_목록을_반환한다() throws Exception {
        // given
        given(reservationService.findAll())
                .willReturn(List.of());

        // when & then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservations.length()").value(0));

        then(reservationService).should().findAll();
    }

    @Test
    void 예약_추가_요청이_성공하면_상태코드_201과_생성된_예약을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", RESERVATION_TIME.toString());
        ReservationCreateCommand expectedCommand = new ReservationCreateCommand("브라운", RESERVATION_DATE, RESERVATION_TIME);

        given(reservationService.addReservation(expectedCommand))
                .willReturn(new Reservation(1L, "브라운", RESERVATION_DATE, RESERVATION_TIME));

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("브라운"))
                .andExpect(jsonPath("$.date").value(RESERVATION_DATE.toString()))
                .andExpect(jsonPath("$.time").value(RESERVATION_TIME.toString()));

        then(reservationService).should().addReservation(expectedCommand);
    }

    @Test
    void 날짜_형식이_올바르지_않으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest("2027/08/15", "브라운", RESERVATION_TIME.toString());

        // when & then
        assertBadReservationRequest(request, "요청 본문 형식이 올바르지 않습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 시간_형식이_올바르지_않으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", "10-00");

        // when & then
        assertBadReservationRequest(request, "요청 본문 형식이 올바르지 않습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 이름이_비어있으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "", RESERVATION_TIME.toString());

        // when & then
        assertBadReservationRequest(request, "예약자 이름은 비어 있을 수 없습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 이름에_숫자나_특수기호가_포함되면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운1", RESERVATION_TIME.toString());

        // when & then
        assertBadReservationRequest(request, "예약자 이름 형식이 올바르지 않습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 서비스에서_예약_충돌_예외가_발생하면_409를_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", RESERVATION_TIME.toString());

        given(reservationService.addReservation(any()))
                .willThrow(new ReservationConflictException("이미 예약된 시간입니다."));

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 예약된 시간입니다."));
    }

    @Test
    void 서비스에서_잘못된_예약_예외가_발생하면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", RESERVATION_TIME.toString());

        given(reservationService.addReservation(any()))
                .willThrow(new BadRequestException("잘못된 예약입니다."));

        // when & then
        assertBadReservationRequest(request, "잘못된 예약입니다.");
    }

    @Test
    void 예약_삭제_요청에_성공하면_상태코드_204를_반환한다() throws Exception {
        // given
        long id = 1L;

        // when & then
        mockMvc.perform(delete("/reservations/{id}", id))
                .andExpect(status().isNoContent());

        then(reservationService).should().deleteReservation(id);
    }

    @Test
    void 예약_삭제_요청_id가_숫자가_아니면_400을_반환한다() throws Exception {
        // when & then
        mockMvc.perform(delete("/reservations/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("요청 값의 형식이 올바르지 않습니다."));

        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 서비스에서_예약_없음_예외가_발생하면_404를_반환한다() throws Exception {
        // given
        long id = -1L;

        willThrow(new ReservationNotFoundException("해당 예약을 찾을 수 없습니다."))
                .given(reservationService).deleteReservation(id);

        // when & then
        mockMvc.perform(delete("/reservations/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 예약을 찾을 수 없습니다."));

        then(reservationService).should().deleteReservation(id);
    }

    private void assertBadReservationRequest(String request, String message) throws Exception {
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    private String createReservationRequest(String date, String name, String time) {
        return """
                {
                    "date": "%s",
                    "name": "%s",
                    "time": "%s"
                }
                """.formatted(date, name, time);
    }
}
