package cholog.member;

import cholog.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    private AtomicLong index;

    @BeforeEach
    void setUp() {
        index = new AtomicLong(0);
    }

    @AfterEach
    void tearDown() {
        index = null;
    }

    @Test
    @DisplayName("수정 이후에도 논리적으로 같은 객체인지 비교")
    public void updateAndCompareMember() {
        // given
        Member before = createMember("Alice", 20);
        Member after = new Member("Bob", 30);

        // when
        Member updated = before.update(after);

        // then
        assertThat(before).isEqualTo(updated);

    }

    private Member createMember(String name, Integer age) {
        return Member.toEntityWithId(index.incrementAndGet(), new Member(name, age));
    }
}
