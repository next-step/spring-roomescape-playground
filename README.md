# 🚪 방탈출 예약 시스템

## 프로젝트 개요

방탈출 예약 관리를 위한 어드민 웹 애플리케이션입니다. 홈 화면 제공부터 예약 조회, 추가, 취소까지의 흐름을 REST API로 구현하며, 잘못된 요청에 대한 예외 처리를 포함합니다.

---

## 주요 기능

### 1단계: 홈화면

- `localhost:8080` 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.

### 2단계: 예약 조회

- `/reservation` 요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
- 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.

### 3단계: 예약 추가 / 취소

- API 명세를 따라 예약 추가 API와 삭제 API를 구현한다.

### 4단계: 예외 처리

- 예약 관련 API 호출 시 발생하는 예외들을 처리한다.

---

## 프로젝트 구조

```
src/main/java/roomescape
├── controller
│   ├── HomeController.java                 # 메인 화면 관련 endpoint를 처리하는 컨트롤러
│   └── ReservationController.java          # 예약 관련 endpoint를 처리하는 컨트롤러
├── domain
│   ├── Reservation.java                    # 예약 데이터를 저장하기 위한 클래스
│   └── Reservations.java                   # Reservation을 관리하는 일급 컬렉션
├── dto
│   ├── ErrorResponse.java                  # 오류 응답 정보를 담을 객체
│   ├── ReservationRequest.java             # 예약 생성 요청 정보를 담을 객체
│   └── ReservationResponse.java            # 예약 생성 요청 응답 정보를 담을 객체
├── exception
│   ├── GlobalExceptionHandler.java         # 공통 예외 핸들러
│   ├── ReservationNotFoundException.java   # 예약을 찾을 수 없을 때 발생하는 Custom Exception
│   └── ReservationValidationException.java # 예약 값 검증에 실패했을 때 발생하는 Custom Exception
├── service
│   └── ReservationService.java             # 예약 관련 비즈니스 로직을 담은 객체
└── RoomeescapeApplication.java             # Main entrypoint
```