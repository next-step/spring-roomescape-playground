## Spring MVC 방탈출 미션
### Step 1
- [x] 웹 관련 의존성 작성
- [x] localhost:8080 요청 시, 어드민 페이지 리다이렉트
- [x] 일단계 테스트 성공

### Step 2
- [x] 어드민 메인 페이지는 templates/reservation.html 파일로 리다이렉트
- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 구현

---
### Review 1
- [x] 의미가 불명확한 코드에 의미를 명확히 함
  - `toEntity()` -> `toEntityWithId()`
  - `Member` 클래스의 ID를 제외한 모든 필드 생성자를 `public` -> `private`
  - `ResponseEntity<?>`에서 제네릭을 와일드카드에서 구체화 클래스로 변경
- [x] 전체 프로덕션 코드에서 쓰이지 않는 코드 삭제
  - `Member` 클래스의 기본 생성자 삭제
- [x] 쓰임이 잘못된 코드 변경
  - `Member.update()` 메소드를 방어적 복사에서 원본 객체 수정, 반환으로 수정
  - `MemberController` 클래스에서 `Repository` 역할까지 겸임하고 있던 것을 분리
- [x] DTO를 사용하고 있지 않던 타입 불명확 API를 DTO를 사용하게끔 수정
  - 테스트 클래스에도 수정 전파

### Review 2
- [ ] in - memory로 엔터티 관리함에 있어서, `동일한 엔터티`의 기준 확립
  - DB 기준에서는 기본 키가 해당 레코드의 `고유한 필드`이므로, 기본 키가 같다면, 같은 엔터티이다.
- [ ] HTTP 인수 테스트를 제외하고도, `Repository`, `Service` 로직을 직접 테스트하는 단위 테스트 작성