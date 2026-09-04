package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:reservation-controller-test")
@AutoConfigureMockMvc
@Transactional
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("같은 날짜와 시간에는 중복으로 예약할 수 없다")
    void rejectsDuplicateReservation() throws Exception {
        long timeId = createdTimeId("15:40");
        createReservation("브라운", "2099-08-13", timeId)
                .andExpect(status().isCreated());

        createReservation("민트", "2099-08-13", timeId)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 예약된 날짜와 시간입니다."));
    }

    private long createdTimeId(String time) throws Exception {
        MvcResult result = mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("time", time))))
                .andExpect(status().isCreated())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    private ResultActions createReservation(String name, String date, long timeId) throws Exception {
        return mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "name", name,
                        "date", date,
                        "time", timeId
                ))));
    }
}
