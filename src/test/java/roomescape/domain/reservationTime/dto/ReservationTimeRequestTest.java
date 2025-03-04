package roomescape.domain.reservationTime.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import roomescape.domain.reservationTime.controller.ReservationTimeController;
import roomescape.domain.reservationTime.service.ReservationTimeService;

@WebMvcTest(ReservationTimeController.class)
class ReservationTimeRequestTest {

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void DTO_검증_테스트() throws Exception {
        String reservationTimeJson = "{\"time\":\"\"}";

        MvcResult result = mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationTimeJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        Throwable resolvedException = result.getResolvedException();

        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) resolvedException;

        List<String> list = getExceptionMessages(exception);
        assertThat(list).containsExactlyInAnyOrder("[time] 예약 시간은 필수 입력값입니다.");
    }

    private static List<String> getExceptionMessages(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();
    }
}