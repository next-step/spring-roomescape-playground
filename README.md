# 요구사항 정리
1. localhost:8080 요청 시 메인 페이지가 응답하도록 구현한다.
2. `localhost:8080/reservation` 요청 시 예약 페이지가 응답하도록 구현한다.
3. 예약 페이지 조회 기능을 구현한다.




--------------------------------

## 1단계

### 1. feat : 메인 페이지 응답 구현

- `GET /` 요청을 `home.html` 뷰로 매핑

1. 의존성 추가
~~~java
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
~~~

2. HomeController 클래스 추가
~~~java
@Controller
public class HomeController {
    @GetMapping("/")
    public String world() {
        return "home";
    }
}
~~~

## 2단계

### 2. feat : 예약 페이지 응답 구현
- `HomeController.java`에서 `/reservation` 페이지 매핑

1. HomeController.java 수정
~~~java
package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String world() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
}
~~~

### 3. feat : 예약 페이지 조회 기능 구현
- `Reservation.java`, `ReservationController.java` 파일 생성
1. Reservation.java
~~~java
package roomescape;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getDate() { 
        return date; 
    }
    
    public String getTime() { 
        return time; 
    }
}
~~~

2. ReservationController.java
~~~java
@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>(List.of(
        new Reservation(1L, "브라운", "2023-01-01", "10:00"),
        new Reservation(2L, "브라운", "2023-01-02", "11:00"),
        new Reservation(3L, "브라운", "2023-01-03", "12:00")
    ));

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }
}
~~~

