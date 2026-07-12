package roomescape.db.time.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TimeEntity {

    private final long id;

    private final String time;
}
