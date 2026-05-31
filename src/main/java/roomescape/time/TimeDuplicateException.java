package roomescape.time;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;
import roomescape.time.domain.TimeException;
import roomescape.time.domain.TimeId;

public class TimeDuplicateException extends ApiException {
    private final @Nullable TimeId previous;

    public TimeDuplicateException(TimeException.DuplicateTime e) {
        super("해당 시간이 이미 존재합니다.");
        this.previous = e.previous;
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.CONFLICT);
        if(previous != null) {
            // TODO: 이 경로를 추상화
            builder.header("Location", "/times/" + previous.id());
        }
        return builder.body(new Dto());
    }

    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "Time.Duplicate";
        }
    }
}
