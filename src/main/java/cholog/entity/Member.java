package cholog.entity;

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

    public boolean equals(Member other) {
        if (other == null) {
            return false;
        }
        if (other.id.equals(this.id)
                && other.name.equals(this.name)
                && other.age.equals(this.age)
        ) {
            return false;
        }
        return true;
    }
}
