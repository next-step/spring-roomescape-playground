package cholog.config;

import cholog.repository.MemberRepository;
import cholog.service.MemberService;
import cholog.service.MemberServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public MemberRepository chologMemberRepository() {
        return new  MemberRepository();
    }

    @Bean
    public MemberService chologMemberService() {
        return new MemberServiceV1(chologMemberRepository());
    }
}
