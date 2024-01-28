package roomescape.configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidateDateFormat, String> {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(final ValidateDateFormat constraintAnnotation) {
        final String customPattern = constraintAnnotation.pattern();
        if (customPattern == null || customPattern.isBlank()) {
            return;
        }

        dateFormatter = DateTimeFormatter.ofPattern(customPattern);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            dateFormatter.parse(value);

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
