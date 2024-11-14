package roomescape.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import roomescape.domain.Time;

@Builder
public record TimeResponseDto( Long id, String time) {

  public static TimeResponseDto from(roomescape.domain.Time time) {

    return TimeResponseDto.builder()
        .id(time.getId())
        .time(time.getTime())
        .build();
  }

  public static List<TimeResponseDto> from(List<Time> times) {
    return times.stream()
        .map(TimeResponseDto::from).collect(
            Collectors.toList());
  }
}
