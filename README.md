# Spring Core 

<h3>8단계 요구사항</h3>

방탈출 시간표가 선택하는 방식으로 수정하기

- [x] API 명세를 따라 시간 관리 API를 구현
  - [x] 시간 조회 API 구현
  - [x] 시간 추가 API 구현
  - [x] 시간 삭제 API 구현 
- [x] 시간 데이터베이스 스키마 추가 
- [x] 요구사항 Test 추가

<h3>9단계 요구사항</h3>

기존 코드 수정
: 기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정

- [ ] templates/reservation.html 대신 templates/new-reservation.html 파일 사용
- [ ] 테이블 스키마 재정의
- [ ] 시간 타입을 String에서 Time 객체로 수정
- [ ] 예약 쿼리 수정
- [ ] 요구사항 test추가