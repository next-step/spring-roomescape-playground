package roomescape.configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatValidator implements ConstraintValidator<ValidateTimeFormat, String> {

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public void initialize(final ValidateTimeFormat constraintAnnotation) {
        final String customPattern = constraintAnnotation.pattern();
        if (customPattern == null || customPattern.isBlank()) {
            return;
        }

        timeFormatter = DateTimeFormatter.ofPattern(customPattern);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            timeFormatter.parse(value);

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
