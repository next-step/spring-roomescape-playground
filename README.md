# 1단계 - 홈 화면

## 구현 목표
사용자가 웹 브라우저에서 `/` 주소로 GET 요청을 보내면
Spring Controller가 요청을 처리하고 `home.html` 화면을 반환하도록 구현한다.

## 구현 내용
- [x] `HomeController` 생성
- [x] `@Controller`를 이용하여 Spring Controller 등록
- [x] `@GetMapping("/")`을 이용하여 `/` GET 요청 처리
- [x] `home.html`을 반환하여 홈 화면 출력


# 2단계 - 예약 조회

## 구현 목표
예약 관리 페이지를 제공하고,
예약 데이터를 Java 객체로 관리하여 API를 통해 반환한다.

## 구현 내용
- [x] Reservation 클래스 생성
- [x] 예약 정보를 객체로 표현
- [x] ReservationController 생성
- [x] /reservation GET 요청 처리
- [x] /reservations GET 요청 처리
- [x] 여러 개의 예약 데이터를 List에 저장
- [x] 예약 목록을 JSON 형태로 반환
