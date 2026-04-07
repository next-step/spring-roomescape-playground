package roomescape;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.controller.ReservationController;
import roomescape.dto.reservationDto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.service.ReservationService;
import roomescape.Manager.IdempotencyManager;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@Import({WebConfig.class, IdempotencyInterceptor.class})
public class IdempotencyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @SpyBean
    private IdempotencyManager idempotencyManager;

    @Test
    @DisplayName("동일한 멱등성 키로 두 번 요청하면 서비스 호출은 한 번만 발생하고 동일한 응답을 보낸다")
    void doubleRequestWithSameKey() throws Exception {
        // Given
        String key = "test-idempotency-key-123";
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(12, 30);

        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L);
        Reservation mockResult = new Reservation(1L,"브라운", date, Time.of(1L, time));

        // 첫 번째 호출에 대한 모킹
        given(reservationService.addReservation(any(),eq(key))).willReturn(mockResult);

        // When & Then: 첫 번째 요청
        mockMvc.perform(post("/reservations")
                        .header("Idempotency-Key", key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("브라운"));

        // When & Then: 두 번째 요청 (중복)
        mockMvc.perform(post("/reservations")
                        .header("Idempotency-Key", key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("브라운"));

        // Verify: 서비스 계층의 addReservation은 단 1회만 호출되었는지 검증
        verify(reservationService, times(1)).addReservation(any(),eq(key));
    }
}