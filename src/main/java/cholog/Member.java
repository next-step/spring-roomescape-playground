package cholog;

public class Member {

    private Long id;
    private String name;
    private Integer age;

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

    private Member setId(Long id) {
        this.id = id;
        return this;
    }

    public static Member toEntityWithId(Long id, Member member) {
        return member.setId(id);
    }

    public Member update(Member member) {
        this.id = member.id;
        this.name = member.name;
        this.age = member.age;
        return this;
    }
}
