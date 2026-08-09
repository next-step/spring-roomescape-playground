package cholog.member;

import cholog.entity.Member;
import cholog.repository.MemberRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberRepositoryTest {

    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepository();
    }

    @AfterEach
    void tearDown() {
        memberRepository = null;
    }

    @Test
    @DisplayName("save() 메소드를 호출하면 ID가 부여된다")
    void testSave() {
        // given
        Member member = new Member("Alice", 20);

        // when
        Member saved = memberRepository.save(member);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findById() 메소드를 호출하면 해당 ID를 갖는 객체를 반환한다.")
    void testFindById() {
        // given
        Member member = new Member("Alice", 20);
        Member saved = memberRepository.save(member);

        // when
        Member found = memberRepository.findById(saved.getId())
                .orElseThrow(RuntimeException::new);

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found).isEqualTo(saved);
    }

    @Test
    @DisplayName("저장되지 않은 ID를 호출하면 Optional로 래핑하여 null을 안전하게 반환한다.")
    void testFindByIllegalId() {
        // given
        Long id = 1L;

        // when
        Optional<Member> foundWrappedOptional = memberRepository.findById(id);

        // then
        assertThat(foundWrappedOptional).isEmpty();
    }

    @Test
    @DisplayName("이미 저장한 엔터티를 다시 저장하려고 하면 예외가 발생한다.")
    void testAlreadyExists() {
        // given
        Member member = new Member("Alice", 20);
        Member exists = Member.toEntityWithId(1L, new Member("Alice", 20));

        // when
        memberRepository.save(member);

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> memberRepository.save(exists)
        );
    }

    @Test
    @DisplayName("findAll()을 호출하면, 저장한 모든 엔터티를 반환한다.")
    void testFindAll() {
        // given
        Member member1 = new Member("Alice", 20);
        Member member2 = new Member("Bob", 20);

        Member member1Saved = memberRepository.save(member1);
        Member member2Saved = memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.contains(member1Saved)).isTrue();
        assertThat(members.contains(member2Saved)).isTrue();
        assertThat(members.get(0)).isEqualTo(member1Saved);
        assertThat(members.get(1)).isEqualTo(member2Saved);
        assertThat(members.get(0).getId()).isEqualTo(member1Saved.getId());
        assertThat(members.get(1).getId()).isEqualTo(member2Saved.getId());
    }

    @Test
    @DisplayName("update()를 호출하면, 수정 전과 수정 후의 id는 동일하다")
    void testUpdate() {
        // given
        Member member = new Member("Alice", 20);
        Member saved = memberRepository.save(member);
        Member updated = new Member("Bob", 20);

        // when
        Member after = memberRepository.update(1L, updated);

        // then
        assertThat(after.getId()).isEqualTo(saved.getId());
        assertThat(after.getName()).isEqualTo(updated.getName());
        assertThat(after.getAge()).isEqualTo(updated.getAge());
    }

    @Test
    @DisplayName("delete()를 호출하면, 기존에 저장하고 있던 엔터티 레코드를 지운다.")
    void testDelete() {
        // given
        Member member = new Member("Alice", 20);
        Member saved = memberRepository.save(member);

        // when
        memberRepository.delete(saved.getId());

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
        assertThat(memberRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("delete()를 잘못된 ID로 호출하면, 기존의 엔터티는 그대로 저장되어 있다.")
    void testDeleteByIllegalId() {
        // given
        Member member = new Member("Alice", 20);
        Member saved = memberRepository.save(member);

        // when
        memberRepository.delete(saved.getId() + 1L);

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
        assertThat(memberRepository.findAll().contains(saved)).isEqualTo(true);
        assertThat(Objects.requireNonNull(memberRepository.findById(saved.getId())
                .orElse(null))).isEqualTo(saved);
    }
}
