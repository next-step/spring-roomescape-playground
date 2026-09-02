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
- 경로 : 루트로('/') GET 요청
- 결과 및 상태코드 : 200
### 2단계
- 1차
  - 경로 : /reservation로 GET 요청
  - 결과 및 상태코드 : 200
- 2차
  - 경로 : /reservations로 GET 요청
  - 결과 : 응답으로 들어와서 하드코딩한 예약 목록이 3개인지 확인
  - 상태코드 : 200
### 3단계
- 1차
  - 경로 : /reservations에 유효한 형식의 content로 POST요청
  - 응답 : /reservations/{id}, 입력한 데이터
  - 상태코드 : 201
- 2차
  - 경로 : /reservations에 GET요청
  - 응답 : 현재 존재하는 reservation들의 내용
  - 상태코드 : 200
- 3차
  - 경로 : /reservations/{id}에 존재하는 id로 DELETE요청
  - 결과 및 상태코드 : 204
- 4차
  - 경로 : /reservations에 GET요청
  - 응답 : 현재 존재하는 reservation들의 내용
  - 상태코드 : 200
### 4단계
- 1차
  - 경로 : /reservations에 유효하지않은 형식의 content로 POST요청
  - 결과 : 응답으로 IllegalArgumentException을 던짐
  - 상태코드 : 400
- 2차
  - 경로 : /reservations/{id}에 존재하지않는 id로 DELETE요청
  - 결과 : 응답으로 NotFoundException을 던짐
  - 상태코드 : 404
---
### 검증범위
1. 루트의('/') GET 요청 검증
2. ReservationController의 /reservation의 GET요청 검증
3. ReservationController의 /reservations의 GET요청 검증
4. ReservationController의 /reservations의 POST요청 검증
5. ReservationController의 /reservations의 DELETE요청 검증
6. ReservationController의 /reservations POST의 예외처리 검증
7. ReservationController의 /reservations DELETE의 예외처리 검증
