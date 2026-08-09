package cholog.dto.response;

import cholog.entity.Member;

public record MemberResponse(
        Long memberId,
        String name,
        Integer age
) {

    public static MemberResponse toDataTransferObject(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getAge());
    }
}
