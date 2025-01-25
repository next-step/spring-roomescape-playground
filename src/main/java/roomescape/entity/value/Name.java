package roomescape.entity.value;

import roomescape.exception.InvalidInputException;

public class Name {

    private final String value;

    private Name(String value) {
        validateInput(value);
        this.value = value;
    }

    public static Name of(String value) {
        return new Name(value);
    }

    private static void validateInput(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("name는 null이거나 공백이 들어갈 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
