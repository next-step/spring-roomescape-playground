package cholog;

public class Member {

    private Long id;
    private String name;
    private Integer age;

    public Member() {
    }

    public Member(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Member(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static Member toEntity(Long id, Member member) {
        return new Member(id, member.name, member.age);
    }

    public Member update(Member member) {
        return new Member(member.name, member.age);
    }
}
