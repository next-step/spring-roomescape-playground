# 방탈출 1,2단계 진행

## 추가한 의존성
- 스프링부트 스타터 웹 : org.springframework.boot:spring-boot-starter-web
- 타임리프 : org.springframework.boot:spring-boot-starter-thymeleaf

## 구현 기능
### 어드민 페이지
- `GET /reservation`
- 화면에 html형식으로 보임
### 예약목록 조회
- `GET /reservations`
- json형식으로 실제 예약된 목록이 보임
### API
- 요청 : GET /reservations
- 응답예시
```
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-01-01",
    "time": "10:00"
  }
]
```

## 테스트 방법
```
.\gradlew.bat test
```
### 1단계
- 목적 : 루트로('/') GET 요청을 하면 응답을 하는지 확인
- 결과 : status코드가 200이면 성공
### 2단계
- 1차
  - /reservation로 GET 요청을 하면 응답(200)을 하는지 확인
- 2차
  - /reservations로 GET 요청을 하면 응답(200)을 하는지 확인
  - 응답으로 들어와서 하드코딩한 예약 목록이 3개인지 확인
---
### 검증범위
1. 루트의('/') GET 요청 검증
2. ReservationController의 /reservation의 GET요청 검증
3. ReservationController의 /reservations의 GET요청 검증
