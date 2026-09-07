package roomescape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;
import roomescape.service.ReservationService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static final Long TIME_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationController reservationController;

    @MockBean
    private ReservationService reservationService;

    @Test
    void ReservationController는_JdbcTemplate을_필드로_가지지_않는다() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void 예약_목록_조회_요청_시_예약_목록을_반환한다() throws Exception {
        // given
        given(reservationService.findAll())
                .willReturn(List.of(
                        new Reservation(1L, "브라운", RESERVATION_DATE, new Time(TIME_ID, RESERVATION_TIME)),
                        new Reservation(2L, "철수", RESERVATION_DATE.plusDays(1), new Time(TIME_ID, RESERVATION_TIME)),
                        new Reservation(3L, "영희", RESERVATION_DATE.plusDays(2), new Time(TIME_ID, RESERVATION_TIME))
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
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", TIME_ID);
        ReservationCreateCommand expectedCommand = new ReservationCreateCommand("브라운", RESERVATION_DATE, TIME_ID);

        given(reservationService.createReservation(expectedCommand))
                .willReturn(new Reservation(1L, "브라운", RESERVATION_DATE, new Time(TIME_ID, RESERVATION_TIME)));

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

        then(reservationService).should().createReservation(expectedCommand);
    }

    @Test
    void 날짜_형식이_올바르지_않으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest("2027/08/15", "브라운", TIME_ID);

        // when & then
        assertBadReservationRequest(request, "GLOBAL_INVALID_BODY", "요청 본문 형식이 올바르지 않습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 시간대_id가_비어있으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", null);

        // when & then
        assertBadReservationRequest(request, "GLOBAL_BAD_REQUEST", "예약 시간대 ID는 비어 있을 수 없습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 이름이_비어있으면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "", TIME_ID);

        // when & then
        assertBadReservationRequest(request, "GLOBAL_BAD_REQUEST", "예약자 이름은 비어 있을 수 없습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 이름에_숫자나_특수기호가_포함되면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운1", TIME_ID);

        // when & then
        assertBadReservationRequest(request, "GLOBAL_BAD_REQUEST", "예약자 이름 형식이 올바르지 않습니다.");
        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 서비스에서_예약_충돌_예외가_발생하면_409를_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", TIME_ID);

        given(reservationService.createReservation(any()))
                .willThrow(new ReservationException(ReservationErrorCode.RESERVATION_CONFLICT));

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("RESERVATION_CONFLICT"))
                .andExpect(jsonPath("$.message").value("이미 예약된 시간입니다."));
    }

    @Test
    void 서비스에서_잘못된_예약_예외가_발생하면_400을_반환한다() throws Exception {
        // given
        String request = createReservationRequest(RESERVATION_DATE.toString(), "브라운", TIME_ID);

        given(reservationService.createReservation(any()))
                .willThrow(new ReservationException(ReservationErrorCode.RESERVATION_IN_PAST));

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("RESERVATION_IN_PAST"))
                .andExpect(jsonPath("$.message").value("과거 시간은 예약할 수 없습니다."));
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
                .andExpect(jsonPath("$.code").value("GLOBAL_INVALID_PARAMETER"))
                .andExpect(jsonPath("$.message").value("요청 값의 형식이 올바르지 않습니다."));

        then(reservationService).shouldHaveNoInteractions();
    }

    @Test
    void 서비스에서_예약_없음_예외가_발생하면_404를_반환한다() throws Exception {
        // given
        long id = -1L;

        willThrow(new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND))
                .given(reservationService).deleteReservation(id);

        // when & then
        mockMvc.perform(delete("/reservations/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("RESERVATION_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("해당 예약을 찾을 수 없습니다."));

        then(reservationService).should().deleteReservation(id);
    }

    @Test
    void 지원하지_않는_HTTP_메서드면_405를_반환한다() throws Exception {
        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code").value("GLOBAL_METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.message").value("지원하지 않는 HTTP 메서드입니다."));
    }

    @Test
    void 지원하지_않는_ContentType이면_415를_반환한다() throws Exception {
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("잘못된 요청"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code").value("GLOBAL_UNSUPPORTED_MEDIA_TYPE"))
                .andExpect(jsonPath("$.message").value("지원하지 않는 Content-Type입니다."));
    }

    private void assertBadReservationRequest(String request, String code, String message) throws Exception {
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(code))
                .andExpect(jsonPath("$.message").value(message));
    }

    private String createReservationRequest(String date, String name, Long timeId) {
        return """
                {
                    "date": "%s",
                    "name": "%s",
                    "timeId": %s
                }
                """.formatted(date, name, timeId == null ? "null" : timeId);
    }
}
