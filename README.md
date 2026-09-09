# 방탈출 어드민 애플리케이션 미션

## 기능 목록

### `HomeController`

- [x] `/` 요청 시 어드민 메인 페이지를 응답한다.
- [x] `/reservation` 요청 시 예약 관리 페이지를 응답한다.
- [x] `/reservations` 요청 시 예약 목록을 응답한다.
- [x] 예약을 추가한다.
- [x] 예약을 삭제한다.
- [x] 예약 정보가 비어 있다면 예외 처리한다.

### `Reservation`

- [x] 예약 번호를 관리한다.
- [x] 예약자 이름을 관리한다.
- [x] 예약 날짜를 관리한다.
- [x] 예약 시간을 관리한다.
- [x] 예약 생성 시 새로운 id을 부여한다.

### `NotFoundReservationException`

- [x] 삭제할 예약을 찾을 수 없는 경우 예외를 발생시킨다.

### `GlobalExceptionHandler`

- [x] 존재하지 않는 예약을 취소하는 경우 404 Not Found를 응답한다.

### `Gradle`

- [x] Spring Web 의존성을 추가한다.
- [x] Thymeleaf 의존성을 추가한다.
- [x] Spring JDBC 의존성을 추가한다.
- [x] H2 의존성을 추가한다.
- [x] Bean Validation 의존성을 추가한다.
- [x] Lombok 의존성을 추가한다.

### `데이터베이스 적용`

- [x] schema.sql 파일을 생성한다.
- [x] 예약 테이블을 생성한다.
- [x] h2 console을 활성화한다.
- [x] datasource url을 지정한다.

### `예약 조회 데이터베이스 적용`

- [x] 데이터베이스의 전체 예약을 조회하는 메서드를 구현한다.

### `예약 추가 및 취소 데이터베이스 적용`

- [x] 기존 List와 AtomicLong을 제거한다.
- [x] 데이터베이스에 예약을 추가한다.
- [x] 조회한 id를 가지고 데이터베이스의 예약을 삭제한다.
- [x] KeyHolder를 이용해 예약 추가 시 생성된 id를 조회하여 사용한다.
- [x] 기존 예약 추가 및 삭제 로직 제거 후 발생하는 오류를 데이터베이스 방식으로 수정한다.

### `시간 관리 기능`

- [x] 시간 테이블을 생성한다.
- [x] 시간 정보를 추가한다.
- [x] 시간 목록을 조회한다.
- [x] 시간을 삭제한다.
- [x] 시간 관리 API를 구현한다.
- [x] 시간 데이터 접근 클래스를 `@Repository`로 Spring Bean으로 등록한다.
- [x] `TimeController`에 `TimeDao`를 생성자 주입한다.

### `예약 시간 선택 기능`

- [x] 예약 관리 페이지에서 `new-reservation.html`을 사용한다.
- [x] 예약 테이블의 시간 정보를 `time_id` 외래키로 변경한다.
- [x] `Reservation`의 시간 타입을 `String`에서 `Time`으로 변경한다.
- [ ] 예약 추가 시 `Time`의 식별자를 저장하도록 수정한다.
- [ ] 예약 조회 시 `time` 테이블을 조인하여 시간 정보를 함께 조회한다.
- [ ] 예약과 시간의 의존 관계에 따라 영향을 받는 로직을 수정한다.

### `1~4단계 리뷰 반영`

- [x] 예약 조회 시 예약 데이터가 중복으로 추가되지 않도록 수정한다.
- [x] 예약 날짜 매개변수를 date로 통일한다.
- [x] 현재 사용하지 않는 toEntity() 메서드를 제거한다.
- [x] 컨트롤러 예외 처리 범위 기준을 정한다.

### `5~7단계 리뷰 반영`

- [x] DTO에 Bean Validation을 적용하여 빈 값과 null을 검증한다.
- [x] `NotFoundReservationException`과 `IllegalArgumentException`의 예외 처리 위치를 수정한다.
- [x] `HomeController`의 책임을 분리한다.
- [x] 메서드 접근 제한자를 수정한다.
- [x] 중복되는 `insert()` 메서드를 제거한다.
- [x] `JdbcTemplate` 필드를 `final`로 선언한다.
- [x] id 값을 처리하기 위한 방식을 비교해 보고 적절한 방식을 적용한다.
- [x] DB 접근 클래스를 `@Repository`로 Spring Bean으로 등록한다.
- [x] `@RequiredArgsConstructor`를 적용하여 생성자를 주입한다.
