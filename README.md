## 계층별 역할

Controller
- HTTP 요청/응답 처리
- DTO 받기
- ResponseEntity 만들기

Service
- 예약 생성/삭제/조회 흐름 처리
- Time 조회 후 Reservation 생성
- 예외 처리 흐름 조정
- 트랜잭션 적용

Repository
- SQL 작성
- JdbcTemplate 사용
- DB 데이터 조회/저장/삭제

Domain
- 이름 검증
- 예약 시간이 과거인지 판단
- Reservation, Time 객체의 규칙 담당