package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateReservationInfo(@Nonnull String name, @Nonnull LocalDateTime time) {
    public static final int NAME_MAX_LENGTH = 20;

    public CreateReservationInfo {
        Objects.requireNonNull(name, "name이 null일 수 없습니다.");
        Objects.requireNonNull(time, "time이 null일 수 없습니다.");

        checkName(name);
    }

    private void checkName(String name) {
        if (name.length() > NAME_MAX_LENGTH) {
            throw new ReservationException.InputFormat.NameTooLong(NAME_MAX_LENGTH);
        }

        boolean isLetter = name.codePoints().allMatch(Character::isLetter);
        if (!isLetter) {
            throw new ReservationException.InputFormat.IllegalName();
        }
    }
}
