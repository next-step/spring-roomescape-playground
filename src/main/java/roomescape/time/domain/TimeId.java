package roomescape.time.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record TimeId(@JsonValue long id) {
    @JsonCreator
    public TimeId {
    }
}
