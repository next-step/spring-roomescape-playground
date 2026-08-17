package cholog.dto;

import cholog.dto.request.MemberRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("이름 필드는 null값을 허용하지 않는다")
    void nameMustNotBeNullInMemberRequest() {

        // given
        String name = null;
        Integer age = 20;

        MemberRequest request = new MemberRequest(name, age);

        // when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 비어있을 수 없다")
    void nameMustRequiredInMemberRequest() {

        // given
        String name = "";
        Integer age = 20;

        // when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(new MemberRequest(name, age));

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 최대 20자를 초과할 수 없다")
    void nameCannotExceed20CharInMemberRequest() {

        // given
        String name = "abcdefghijklmnopqrstu";
        Integer age = 20;

        //when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(new MemberRequest(name, age));

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("나이 필드는 null값을 허용하지 않는다")
    void ageMustNotBeNullInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = null;

        // when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(new MemberRequest(name, age));

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("나이 필드는 음수값을 허용하지 않는다")
    void ageMustNotBeNegativeInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = -1;

        // when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(new MemberRequest(name, age));

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("멤버 생성 요청이 정상적으로 처리된 경우")
    void allValidatedInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = 20;

        // when
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(new MemberRequest(name, age));

        // then
        assertThat(violations).hasSize(0);
    }
}
