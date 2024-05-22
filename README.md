## 1단계 홈 화면
- [x] 타임리프 모듈을 추가한다.

## 2단계 예약 조회
- [x] `/reservation GET` 요청 시, 예약 페이지(템플릿 파일)를 반환한다.
- [x] `/reservations GET` 요청 시, 예약 목록을 조회하는 API를 추가한다.
  - DB(`ArrayList`)에 저장된 예약자 명단을 `JSON` 타입으로 반환한다.
  - DTO 리스트를 반환한다. (`@ResponseBody`)

## 3단계 예약 추가 / 취소
- [x] `/reservations POST` 요청 시, 입력받은 JSON을 바탕으로 DB에 저장한다.
  - 201 반환
  - `Location` 응답 헤더에 `/reservations/{newId}` 추가하기
- [x] `/reservations/{1} DELETE` 요청 시, 입력받은 ID에 해당하는 객체를 DB에서 삭제한다.
  - 204 반환

## 4단계 예외 처리
- [x] 사용자가 잘못된 API 요청을 할 경우, 400 에러를 반환한다.
  - 예약 추가 시, 필수 인자 값이 부족할 경우
    - 날짜 누락
    - 이름 누락
    - 시간 누락
  - 예약 삭제 시, DB에 없는 ID 값을 요청할 경우
  - (필요한 경우, 더 만들어보기)
- [x] 400을 반환하도록 하는 커스텀 예외를 생성한다.

- [x] `@ControllerAdvice` 학습하기

## 5단계 데이터베이스 적용하기
- [x] h2 DB 연결을 위한 의존성 추가 및 SQL 스크립트를 추가한다.

## 6단계 데이터 조회하기
- [x] Repository 객체의 메서드를 `SELECT` 문과 `JdbcTemplate` 객체를 활용하여 DB에 접근하는 로직으로 변경한다.
- [x] `/reservations GET` API의 로직은 변경하지 않는다.

## 7단계 데이터 추가/삭제하기
- [x] `H2ReservationRepository`의 `save` 메서드를 `INSERT` 문과 `JdbcTemplate`를 활용하여 DB에 접근하는 로직으로 변경한다.
- [x] `H2ReservationRepository`의 `deleteById` 메서드를 `DELETE` 문과 `JdbcTemplate`를 활용하여 DB에 접근하는 로직으로 변경한다.

## 리팩토링
- [x] `NamedParameterJdbcTemplate` 학습 후 변경
- [x] `Record` 타입 변경
- [x] 정적 팩토리 메서드 변경
- [x] `Simplejdbcinsert` 학습 후 변경
- [ ] `MVC` 및 `JDBC` 리뷰 참고하여 로직 수정