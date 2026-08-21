## Spring MVC Mission 5, 6, 7
### Review 1
- [x] `RoomEscapeApplication.java`를 꺼냄
  - com.cholog.roomescape라고 하는 프로젝트 이름과 아티팩트를 심어 `RoomEscapeApplication.java`에 패키지 경로 작성
- [x] GlobalExceptionHandler에서 제어하는 `MethodArgumentNotValidException.java` 예외를 직접 검증하는 `RestAssured` 테스트 작성
- [x] `GlobalExceptionHandler`에 에러 응답을 빌드하는 모든 책임을 전가
- [x] 잘못된 예외 핸들러 메소드 이름 수정 (handleRoomEscapeException -> handleMethodArgumentNotValid)
- [x] `ReservationRepositoryimpl.java` 중복 빈 등록 제거
- [x] Reservation 생성자에서 nullable = false 검증 로직 추가
- [x] ViewResolver를 담당하는 컨트롤러와 API를 담당하는 컨트롤러 분리