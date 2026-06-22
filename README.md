# 🚪 방탈출 예약 시스템

## 프로젝트 개요

방탈출 예약 관리를 위한 어드민 웹 애플리케이션입니다. 홈 화면 제공부터 예약 조회, 추가, 취소까지의 흐름을 REST API로 구현하며, 잘못된 요청에 대한 예외 처리를 포함합니다.

---

## 주요 기능

### 예약 관리
- 예약 목록을 조회할 수 있습니다.
- 예약자 이름, 예약 날짜, 예약 시간을 입력해 예약을 생성할 수 있습니다.
- 기존 예약을 삭제할 수 있습니다.
- 예약 생성 시 존재하는 예약 가능 시간만 선택할 수 있습니다.
- 과거 날짜와 시간으로는 예약할 수 없도록 검증합니다.

### 예약 가능 시간 관리
- 예약 가능 시간 목록을 조회할 수 있습니다.
- 새로운 예약 가능 시간을 추가할 수 있습니다.
- 기존 예약 가능 시간을 삭제할 수 있습니다.
- 동일한 예약 가능 시간이 중복 등록되지 않도록 검증합니다.

### API와 화면 요청 분리
- 화면을 반환하는 ViewController와 JSON 데이터를 주고받는 ApiController를 분리했습니다.
- 예약 화면과 시간 관리 화면 요청을 각각 별도 컨트롤러에서 처리합니다.
- 예약/시간 관련 REST API는 별도 API 컨트롤러에서 처리합니다.

### 예외 처리
- `ErrorCode` enum을 통해 예외 상황별 HTTP 상태 코드와 메시지를 관리합니다.
- 공통 예외 클래스인 `RoomEscapeException`을 통해 비즈니스 예외를 처리합니다.
- 잘못된 요청, 존재하지 않는 예약/시간, 중복 시간 등록, 과거 시간 예약 등의 예외를 처리합니다.

---

## 프로젝트 구조

```
src/main/java/roomescape
├── controller
│   ├── HomeController.java                 # 홈 화면 요청을 처리하는 컨트롤러
│   ├── ReservationApiController.java       # 예약 관련 API 요청을 처리하는 컨트롤러
│   ├── ReservationViewController.java      # 예약 화면 요청을 처리하는 컨트롤러
│   ├── TimeApiController.java              # 예약 가능 시간 관련 API 요청을 처리하는 컨트롤러
│   └── TimeViewController.java             # 예약 가능 시간 화면 요청을 처리하는 컨트롤러
├── domain
│   ├── Reservation.java                    # 예약 도메인 객체
│   └── Time.java                           # 예약 가능 시간 도메인 객체
├── dto
│   ├── ErrorResponse.java                  # 오류 응답 정보를 담는 객체
│   ├── ReservationRequest.java             # 예약 생성 요청 정보를 담는 객체
│   ├── ReservationResponse.java            # 예약 응답 정보를 담는 객체
│   ├── TimeRequest.java                    # 예약 가능 시간 생성 요청 정보를 담는 객체
│   └── TimeResponse.java                   # 예약 가능 시간 응답 정보를 담는 객체
├── exception
│   ├── ErrorCode.java                      # 예외 상황별 상태 코드와 메시지를 관리하는 enum
│   ├── GlobalExceptionHandler.java         # 공통 예외 핸들러
│   └── RoomEscapeException.java            # ErrorCode 기반의 Custom Exception
├── repository
│   ├── ReservationRepository.java          # 예약 데이터 접근 로직을 담당하는 객체
│   └── TimeRepository.java                 # 예약 가능 시간 데이터 접근 로직을 담당하는 객체
├── service
│   ├── ReservationService.java             # 예약 관련 비즈니스 로직을 담당하는 객체
│   └── TimeService.java                    # 예약 가능 시간 관련 비즈니스 로직을 담당하는 객체
└── RoomescapeApplication.java              # Spring Boot 애플리케이션 진입점
```