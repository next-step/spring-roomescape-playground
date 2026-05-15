# Spring MVC
## 4단계 - 예외 처리

### 요구사항
- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요
- 예를 들면 예약 추가 시 필요한 인자값이 비어있는 경우 혹은 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우가 있습니다.

### 구현 목록
- "필요한 인자값이 비어있는 경우"
  - [x] 각 항목이 비어있는지 확인 `@Valid` 활용
    - `ReservationDto` 필드에 `@NotBlank` 적용, 컨트롤러에서 `@Valid`로 검증
    - `MethodArgumentNotValidException`은 `@ControllerAdvice`(`IncorrectAttributeError`)에서 글로벌 처리 → 필드별 에러 Map 반환
- "삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우"
  - [x] @ExceptionHandler를 활용해 예외 처리
    - `Reservations#removeById`에서 미존재 시 `IllegalArgumentException` throw
    - `ReservationController`의 `@ExceptionHandler`가 잡아 400 응답 (예약 도메인 한정이라 로컬 핸들러로 분리)
