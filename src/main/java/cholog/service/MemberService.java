package cholog.service;

import cholog.dto.MemberRequest;
import cholog.entity.Member;

import java.util.List;

public interface MemberService {

    Member createMember(MemberRequest request);

    List<Member> findAllMembers();

    Member updateMember(Long memberId, MemberRequest request);

    Void deleteMember(Long memberId);
}
