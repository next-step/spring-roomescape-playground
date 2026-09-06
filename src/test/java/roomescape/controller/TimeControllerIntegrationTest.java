package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:time-controller-test")
@AutoConfigureMockMvc
@Transactional
class TimeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("예약 시간 생성 요청에서 시간은 필수이다")
    void rejectsTimeRequestWithoutTime() throws Exception {
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("시간은 필수입니다."));
    }

    @Test
    @DisplayName("시간 생성 응답에는 조회할 수 없는 Location 헤더를 포함하지 않는다")
    void createsTimeWithoutLocationHeader() throws Exception {
        createTime("15:40")
                .andExpect(status().isCreated())
                .andExpect(header().doesNotExist("Location"));
    }

    @Test
    @DisplayName("같은 예약 시간은 중복으로 생성할 수 없다")
    void rejectsDuplicateReservationTime() throws Exception {
        createTime("15:40")
                .andExpect(status().isCreated());

        createTime("15:40")
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 등록된 시간입니다."));
    }

    @Test
    @DisplayName("예약에서 참조 중인 시간은 삭제할 수 없다")
    void rejectsDeletingTimeInUse() throws Exception {
        long timeId = createdTimeId("15:40");
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "name", "브라운",
                                "date", "2099-08-13",
                                "time", timeId
                        ))))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/times/{id}", timeId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("예약에서 사용 중인 시간은 삭제할 수 없습니다. id=" + timeId));
    }

    private ResultActions createTime(String time) throws Exception {
        return mockMvc.perform(post("/times")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("time", time))));
    }

    private long createdTimeId(String time) throws Exception {
        MvcResult result = createTime(time)
                .andExpect(status().isCreated())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }
}
