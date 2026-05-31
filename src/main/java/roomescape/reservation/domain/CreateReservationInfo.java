package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeId;

import java.time.LocalDate;
import java.util.Objects;

public record CreateReservationInfo(@Nonnull String name, @Nonnull LocalDate date, @Nonnull TimeId timeId) {
    public static final int NAME_MAX_LENGTH = 20;

    public CreateReservationInfo {
        Objects.requireNonNull(name, "name이 null일 수 없습니다.");
        Objects.requireNonNull(date, "date가 null일 수 없습니다.");
        Objects.requireNonNull(timeId, "time이 null일 수 없습니다.");

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
