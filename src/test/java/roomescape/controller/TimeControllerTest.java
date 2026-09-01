package roomescape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;
import roomescape.service.TimeService;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeController.class)
class TimeControllerTest {
    private static final LocalTime TIME = LocalTime.of(10, 0);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @Test
    void 시간대_목록_조회_요청_시_시간대_목록을_반환한다() throws Exception {
        // given
        given(timeService.findAll())
                .willReturn(List.of(
                        new Time(1L, LocalTime.of(10, 0)),
                        new Time(2L, LocalTime.of(11, 0)),
                        new Time(3L, LocalTime.of(12, 0))
                ));

        // when & then
        mockMvc.perform(get("/times"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.times.length()").value(3))
                .andExpect(jsonPath("$.times[0].id").value(1))
                .andExpect(jsonPath("$.times[0].time").value("10:00"));

        then(timeService).should().findAll();
    }

    @Test
    void 시간대_목록이_비어있으면_빈_시간대_목록을_반환한다() throws Exception {
        // given
        given(timeService.findAll()).willReturn(List.of());

        // when & then
        mockMvc.perform(get("/times"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.times.length()").value(0));

        then(timeService).should().findAll();
    }

    @Test
    void 시간대_추가_요청이_성공하면_상태코드_201과_생성된_시간대를_반환한다() throws Exception {
        // given
        String request = createTimeRequest(TIME.toString());
        TimeCreateCommand expectedCommand = new TimeCreateCommand(TIME);

        given(timeService.createTime(expectedCommand))
                .willReturn(new Time(1L, TIME));

        // when & then
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.time").value(TIME.toString()));

        then(timeService).should().createTime(expectedCommand);
    }

    @Test
    void 시간_형식이_올바르지_않으면_400을_반환한다() throws Exception {
        // given
        String request = createTimeRequest("10-00");

        // when & then
        assertBadTimeRequest(request, "GLOBAL_INVALID_BODY", "요청 본문 형식이 올바르지 않습니다.");
        then(timeService).shouldHaveNoInteractions();
    }

    @Test
    void 시간대가_비어있으면_400을_반환한다() throws Exception {
        // given
        String request = """
                {
                    "time": null
                }
                """;

        // when & then
        assertBadTimeRequest(request, "GLOBAL_BAD_REQUEST", "시간대는 비어 있을 수 없습니다.");
        then(timeService).shouldHaveNoInteractions();
    }

    @Test
    void 서비스에서_시간대_충돌_예외가_발생하면_409를_반환한다() throws Exception {
        // given
        String request = createTimeRequest(TIME.toString());

        given(timeService.createTime(any()))
                .willThrow(new TimeException(TimeErrorCode.TIME_CONFLICT));

        // when & then
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("TIME_CONFLICT"))
                .andExpect(jsonPath("$.message").value("해당 예약 시간대가 이미 존재합니다."));
    }

    @Test
    void 지원하지_않는_ContentType이면_415를_반환한다() throws Exception {
        mockMvc.perform(post("/times")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("잘못된 요청"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code").value("GLOBAL_UNSUPPORTED_MEDIA_TYPE"))
                .andExpect(jsonPath("$.message").value("지원하지 않는 Content-Type입니다."));
    }

    private void assertBadTimeRequest(String request, String code, String message) throws Exception {
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(code))
                .andExpect(jsonPath("$.message").value(message));
    }

    private String createTimeRequest(String time) {
        return """
                {
                    "time": "%s"
                }
                """.formatted(time);
    }
}
