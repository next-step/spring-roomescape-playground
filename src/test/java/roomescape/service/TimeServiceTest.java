package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Test
    void 시간을_생성할_수_있다() {
        // given
        final CreateTimeRequest request = new CreateTimeRequest(LocalTime.of(13, 0));
        // when
        final TimeResponse response = timeService.createTime(request);
        // then
        assertAll(
                () -> assertThat(response.id()).isNotNull(),
                () -> assertThat(response.time()).isEqualTo(request.time())
        );
    }

    @Test
    void 동일한_시간이_이미_존재한다면_예외가_발생한다() {
        // given
        final CreateTimeRequest request1 = new CreateTimeRequest(LocalTime.of(13, 0));
        timeService.createTime(request1);
        final CreateTimeRequest request2 = new CreateTimeRequest(LocalTime.of(13, 0));
        // when & then
        assertThatThrownBy(() -> timeService.createTime(request2))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(
                        ExceptionMessage.TIME_ALREADY_EXISTS.getMessage());
    }

    @Test
    void 저장한_시간을_모두_조회할_수_있다() {
        // given
        final CreateTimeRequest request1 = new CreateTimeRequest(LocalTime.of(13, 0));
        timeService.createTime(request1);
        final CreateTimeRequest request2 = new CreateTimeRequest(LocalTime.of(14, 0));
        timeService.createTime(request2);
        // when
        final List<TimeResponse> timeResponses = timeService.getTimes();
        // then
        assertThat(timeResponses).hasSize(2);
    }

    @Test
    void 시간을_삭제할_수_있다() {
        // given
        final CreateTimeRequest request = new CreateTimeRequest(LocalTime.of(13, 0));
        final TimeResponse response = timeService.createTime(request);
        final int initialTimeSize = timeService.getTimes().size();
        // when
        timeService.deleteTime(response.id());
        // then
        assertThat(timeService.getTimes()).hasSize(initialTimeSize - 1);
    }

    @Test
    void 존재하지_않는_시간을_삭제하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> timeService.deleteTime(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(
                        ExceptionMessage.TIME_NOT_EXISTS.getMessage());
    }
}
