package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Member;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                               .orElseThrow(() -> new NotFoundDataException("존재하지 않는 회원입니다."));
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name)
                               .orElseThrow(() -> new NotFoundDataException("존재하지 않는 회원입니다."));
    }
}
