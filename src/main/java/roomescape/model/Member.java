package roomescape.model;

public class Member {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Member() {
    }

    public Member(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Member( String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public static Member toEntity(Member member, Long id) {
        return new Member(id, member.name, member.date, member.time);
    }
}
