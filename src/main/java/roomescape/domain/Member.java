package roomescape.domain;

import java.util.Objects;

public class Member {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String role;

    public Member(Long id, String email, String password, String name, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void checkPassword(String passwordToCompare) {
        if (!Objects.equals(this.password, passwordToCompare)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
