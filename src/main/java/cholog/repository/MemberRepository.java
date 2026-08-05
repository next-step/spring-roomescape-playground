package cholog.repository;

import cholog.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemberRepository {

    private final Map<Long, Member> members = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public Member save(Member member) {
        if (member.getId() != null) {
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");
        }

        Member createdMember = Member.toEntityWithId(index.getAndIncrement(), member);
        members.put(createdMember.getId(), createdMember);

        return createdMember;
    }

    public Optional<Member> findById(Long memberId) {
        return members.entrySet().stream()
                .filter(entry -> entry.getKey().equals(memberId))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public List<Member> findAll() {
        return members.values().stream()
                .sorted().toList();
    }

    public Member update(Long memberId, Member member) {
        Member foundMember = members.entrySet().stream()
                .filter(entry -> entry.getKey().equals(memberId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

        return foundMember.update(member);
    }

    public Void delete(Long memberId) {
        members.remove(memberId);
        return null;
    }
}
