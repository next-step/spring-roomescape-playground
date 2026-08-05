package cholog.service;

import cholog.dto.request.MemberRequest;
import cholog.entity.Member;
import cholog.repository.MemberRepository;

import java.util.List;

public class MemberServiceV1 implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceV1(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(MemberRequest request) {
        Member member = MemberRequest.toMemberWithoutId(request);
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMember(Long memberId, MemberRequest request) {
        return memberRepository.update(memberId, MemberRequest.toMemberWithoutId(request));
    }

    @Override
    public Void deleteMember(Long memberId) {
        memberRepository.delete(memberId);
        return null;
    }
}
