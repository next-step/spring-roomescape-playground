package cholog.config;

import cholog.repository.MemberRepository;
import cholog.service.MemberService;
import cholog.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public MemberRepository chologMemberRepository() {
        return new  MemberRepository();
    }

    @Bean
    public MemberService chologMemberService(MemberRepository memberRepository) {
        return new MemberServiceImpl(memberRepository);
    }
}
