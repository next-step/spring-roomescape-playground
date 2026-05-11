# 🚪 방탈출 예약 관리 시스템 (Roomescape Reservation System)

이 프로젝트는 Spring Boot를 기반으로 구현된 간단한 방탈출 예약 관리 시스템입니다. 사용자의 예약을 생성, 조회, 삭제할 수 있는 RESTful API를 제공하며, 객체지향적인 설계 원칙과 계층화된 아키텍처를 적용하여 구축되었습니다.

---

## 🏗️ 아키텍처 및 구조 (Architecture)

프로젝트는 **계층형 아키텍처(Layered Architecture)** 와 **단일 책임 원칙(SRP)** 을 따르도록 패키지와 클래스가 분리되어 있습니다.

```text
src/main/java/roomescape/
├── controller/
│   ├── ReservationController.java      # HTTP 요청 처리 및 응답 반환
│   ├── GreetingViewController.java     # View 렌더링 컨트롤러 (예시)
│   └── ReservationViewController.java  # View 렌더링 컨트롤러 (예시)
├── service/
│   └── ReservationService.java         # 핵심 비즈니스 로직 및 메모리 데이터 관리
├── domain/
│   ├── Reservation.java                # 핵심 도메인 모델 (Entity)
│   └── ReservationValidator.java       # 비즈니스 규칙 및 정책 검증 전담 컴포넌트
├── dto/
│   └── ReservationRequest.java         # 클라이언트 요청 데이터를 담는 객체
└── GlobalExceptionHandler.java         # 전역 예외 처리 (AOP)
```

### 🎯 핵심 설계 포인트

1. **DTO 적용 (역할 분리)**: 클라이언트의 요청 데이터를 받는 `ReservationRequest`와 실제 비즈니스 모델인 `Reservation`을 분리하여 API 스펙의 안정성을 높였습니다.
2. **비즈니스 검증 분리 (`ReservationValidator`)**: 과거 시간 예약 방지, 중복 예약 방지 등 복잡한 비즈니스 규칙을 서비스에서 분리하여 가독성과 유지보수성을 향상시켰습니다.
3. **전역 예외 처리 (`GlobalExceptionHandler`)**: `@RestControllerAdvice`를 활용하여 애플리케이션 전역에서 발생하는 예외를 중앙 집중식으로 처리하고, 안전한 HTTP 상태 코드(400, 404, 500)를 반환합니다.
4. **Spring Validation (`@Valid`)**: `ReservationController`에서 `@Valid`를 사용해 `ReservationRequest`의 필수 값과 형식 검증을 수행합니다. 과거 시간 예약 방지와 중복 예약 방지는 `ReservationValidator`에서 별도로 처리합니다.

---

## 🛡️ 예외 및 검증 정책 (Validation Rules)

1. **입력 형식 검증**: 
   * 이름, 날짜, 시간은 필수 값입니다. (`@NotBlank`)
   * 날짜는 `yyyy-MM-dd` 형식을 지켜야 합니다. (`@Pattern`)
   * 시간은 `HH:mm` 형식을 지켜야 합니다. (`@Pattern`)
2. **비즈니스 규칙 검증**:
   * **과거 시간 예약 방지**: 현재 서버 시간보다 이전 시간으로 예약할 수 없습니다.
   * **중복 예약 방지**: 동일한 날짜와 시간에 이미 등록된 예약이 있다면 추가 예약을 막습니다.
   * **없는 데이터 삭제 방지**: 삭제하려는 예약 ID가 존재하지 않으면 예외를 반환합니다.
