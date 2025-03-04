package roomescape.domain.reservation.dto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import roomescape.domain.reservation.controller.ReservationController;
import roomescape.domain.reservation.service.ReservationService;
import roomescape.domain.reservationTime.service.ReservationTimeService;

@WebMvcTest(ReservationController.class)
class ReservationRequestTest {

    @MockBean
    private ReservationTimeService reservationTimeService;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void DTO_검증_테스트() throws Exception {
        String reservationJson = "{"
                + "\"name\":\"" + "a".repeat(256) + "\","
                + "\"date\":\"\","
                + "\"time\":\"\""
                + "}";

        MvcResult result = mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        Exception resolvedException = result.getResolvedException();

        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) resolvedException;

        List<String> list = getExceptionMessages(exception);
        assertThat(list).containsExactlyInAnyOrder(
                "[date] 예약 날짜는 필수 입력값입니다.",
                "[name] 이름은 255자 이하여야 합니다.",
                "[time] 예약 시간은 필수 입력값입니다.");
    }

    private static List<String> getExceptionMessages(final MethodArgumentNotValidException exception) {

        return exception.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();
    }
}

