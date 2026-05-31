package roomescape.time.domain;

import jakarta.annotation.Nullable;
import roomescape.global.domain.DomainException;

public class TimeException extends DomainException {
    public TimeException(String message) {
        super(message);
    }

    public static class DoesNotExist extends TimeException {
        public DoesNotExist() {
            super("해당 시간이 존재하지 않습니다.");
        }
    }

    public static class DuplicateTime extends TimeException {
        public final @Nullable TimeId previous;

        public DuplicateTime(@Nullable TimeId previous) {
            super("해당 시간이 이미 존재합니다.");
            this.previous = previous;
        }

        public DuplicateTime() {
            this(null);
        }
    }

    public static class IllegalPrecision extends TimeException {
        public IllegalPrecision() {
            super("시간은 시간/분 단위로만 지정 가능합니다.");
        }
    }
}
