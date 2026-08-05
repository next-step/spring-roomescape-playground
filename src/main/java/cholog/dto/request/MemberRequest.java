package cholog.dto.request;

import cholog.entity.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberRequest(
        @NotBlank(message = "이름 필드는 비어있을 수 없습니다.")
        @Size(max = 50, message = "이름 필드는 50자 이하만 가능합니다.")
        String name,

        @NotNull(message = "나이 필드는 비어있을 수 없습니다.")
        @Min(value = 0, message = "나이는 필드는 음이 아닌 정수만 가능합니다.")
        Integer age
) {

    public static Member toMemberWithoutId(MemberRequest request) {
        return new Member(request.name, request.age);
    }
}
