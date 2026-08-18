# 방탈출 예약 관리 시스템

스프링 입문 과정을 진행하며 구현하는 방탈출 서비스 예약 관리 애플리케이션입니다.
---
## 단계별 구현 기능 목록

### 1단계 - 홈 화면 응답
- [x] **웹 환경 의존성 설정**: Gradle(`build.gradle`)에 `spring-boot-starter-web` 및 `thymeleaf` 의존성 추가
- [x] **메인 화면 연결**: `GET /` 요청 시 `templates/home.html` 어드민 메인 페이지 반환
- [x] **학습 테스트 통과**: `PageTest.home` 통과 (200 OK 상태 코드 검증)
---

### 2단계 - 예약 관리 페이지 및 예약 목록 조회 API
- [x] **예약 관리 페이지 연결**: `GET /reservation` 요청 시 `templates/reservation.html` 화면 반환
- [x] **예약 데이터 도메인 구현**: `Reservation` 모델 클래스 작성
- [x] **예약 목록 조회 API 구현**: `GET /reservations` 요청 시 메모리 내 예약 목록(`List<Reservation>`)을 JSON 형식으로 반환 
- [x] **학습 테스트 통과**: `ReservationTest.readReservation` 통과 (목록 크기 검증 `size() == 1`)
---

### 3단계 - 예약 추가 및 취소 기능 구현 
- [x] **예약 추가 API 구현**: `POST/reservations` 요청 시 요청 본문을 역직렬화 하여 'Reservation' 생성 및 메모리 저장
- 응답 상태 코드 `201 Created` 반환
- `Location` 응답 헤더에 URI 포함
- 생성된 예약 객체를 응답 본문으로 반환
- [x] **예약 취소 API 구현**: `DELETE/reservations` 요청시 `id`에 해당하는 예약을 메모리 목록에서 제거 
- 삭제 성공 시 응답 상태 코드 `204 No Content` 반환
- [x] **학습 테스트 통과**: `ReservationTest` 테스트 작성 및 통과
- 예약 추가 검증
- 예약 목록 조회 검증
- 예약 취소 검증 
---

### 4단계 - 예외처리 추가
- [x] **예약 추가 시 잘못된 요청 예외 처리**:
  -`POST/reservations` 요청 시 필수 인자값 누락 및 빈 값, 지난 날짜/시간 검증
  - 유효하지 않은 요청 데이터일 경우 `400 Bad Request` 상태 코드 및 에러 메시지 반환
- [x] **예약 삭제 시 대상이 존재하지 않는 경우 예외처리**:
  - `DELETE/reservations/{id}` 요청 시 전달 받은 `id`에 해당하는 예약이 목록에 없는 경우 검증
  - 존재하지 않는 예약인 경우 `404 Not Found` 상태 코드 반환
- [x] **스프링 예외처리 적용**
  - `@ExceptionHandler`를 활용하여 컨트롤러에서 발생한 예외를 HTTP 응답으로 변환
- [x] **예외 테스트 통과**:
  - 필요한 인자값이 없는 경우 400 상태 코드 검증
  - 삭제할 예약이 없는 경우 404 상태 코드 검증 

