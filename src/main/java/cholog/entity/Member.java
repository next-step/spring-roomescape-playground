package cholog.entity;

import java.util.Objects;

public class Member {

    private Long id;
    private String name;
    private Integer age;

    public Member(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    private Member setId(Long id) {
        this.id = id;
        return this;
    }

    public static Member toEntityWithId(Long id, Member member) {
        return member.setId(id);
    }

    public Member update(Member member) {
        if (member.id != null) {
            this.id = member.id;
        }
        if (member.name != null) {
            this.name = member.name;
        }
        if (member.age != null) {
            this.age = member.age;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member member)) {
            return false;
        }
        return id != null && id.equals(member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
