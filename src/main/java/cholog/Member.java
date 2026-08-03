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

    public Integer getAge() {
        return age;
    }

    public static Member toEntity(Long id, Member member) {
        return new Member(id, member.name, member.age);
    }
}
