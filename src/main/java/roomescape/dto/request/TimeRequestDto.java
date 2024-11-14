package roomescape.dto.request;

import roomescape.domain.Time;

public record TimeRequestDto( String time) {
  public Time toTime() {

    return Time.builder()
        .time(time())
        .build();
  }
}
