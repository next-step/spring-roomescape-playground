# Spring MVC

<h3>1단계 요구사항</h3>

- [x] localhost:8080 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현

<h3>2단계 요구사항</h3>

**예약 페이지**
- [x] /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현
  - 어드민 메인 페이지는 templates/reservation.html 파일 사용
- [x] Thymeleaf 템플릿엔진을 활용하여 페이지를 응답

**예약 목록 조회 API**
- [x] 예약 목록 조회 API , 예약 페이지 요청과 예약 목록 조회 요청 처리 매서드 구현
- [x] 정상 동작 확인을 위해 임의 데이터를 넣어서 확인
- [x] 요구사항 테스트

<h3>3단계 요구사항</h3>

- [x] 예약 추가 API 구현
- [x] 삭제 API 구현
- [x] 요구사항 테스트

<h3>4단계 요구사항</h3>

- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답
  - 필수 인자가 없는경우 
  - 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우

<h3>5단계 요구사항</h3>
h2 데이터베이스를 활용하여 데이터를 저장하도록 수정
- [ ] gradle 의존성 추가
- [ ] 테이블 스키마 정의
- [ ] 데이터베이스 설정
- [ ] 요구 사항 테스트 진행