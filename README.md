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
