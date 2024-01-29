package roomescape.configuration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
public @interface ValidateDateFormat {

    String message() default "날짜 형식이 올바르지 않습니다.";

    String pattern() default "yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
