package cholog.member;

import cholog.dto.request.MemberRequest;
import cholog.entity.Member;
import cholog.repository.MemberRepository;
import cholog.service.MemberService;
import cholog.service.MemberServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberServiceImpl(new MemberRepository());
    }

    @AfterEach
    void tearDown() {
        memberService = null;
    }

    @Test
    @DisplayName("createMember() 메소드를 호출하면, 만들어진 멤버를 반환한다")
    void testCreateMember() {
        // given
        String name = "Alice";
        Integer age = 20;
        MemberRequest request = new MemberRequest(name, age);

        // when
        Member member = memberService.createMember(request);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getAge()).isEqualTo(age);
    }

    @Test
    @DisplayName("findAllMembers()를 호출하면, 저장되어 있는 List<Member>를 반환한다.")
    void testFindAllMembers() {
        // given
        Member member1 = memberService.createMember(new MemberRequest("Alice", 20));
        Member member2 = memberService.createMember(new MemberRequest("Bob", 20));

        // when
        List<Member> members = memberService.findAllMembers();

        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.contains(member1)).isTrue();
        assertThat(members.contains(member2)).isTrue();
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(0).getId()).isEqualTo(member1.getId());
        assertThat(members.get(1)).isEqualTo(member2);
        assertThat(members.get(1).getId()).isEqualTo(member2.getId());
    }

    @Test
    @DisplayName("updateMember()를 호출하면, 수정된 멤버를 반환한다.")
    void testUpdateMember() {
        // given
        Member member = memberService.createMember(new MemberRequest("Alice", 20));
        MemberRequest request = new MemberRequest("Bob", 20);

        // when
        Member updated = memberService.updateMember(member.getId(), request);

        // then
        assertThat(member.getId()).isEqualTo(updated.getId());
        assertThat(request.name()).isEqualTo(updated.getName());
        assertThat(request.age()).isEqualTo(updated.getAge());
    }

    @Test
    @DisplayName("잘못된 ID로 updateMember()를 호출하면, 예외를 던진다.")
    void testUpdateMemberByIllegalId() {
        // given
        MemberRequest updateRequest = new MemberRequest("Alice", 20);

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,

                // when
                () -> memberService.updateMember(-1L, updateRequest)
        );
    }

    @Test
    @DisplayName("deleteMember()를 호출하면, 등록된 레코드를 지운다.")
    void testDeleteMember() {
        // given
        Member created = memberService.createMember(new MemberRequest("Alice", 20));

        // when
        memberService.deleteMember(created.getId());

        // then
        assertThat(memberService.findAllMembers().size()).isEqualTo(0);
    }
}
