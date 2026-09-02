# 방탈출 1-7단계 진행

## 추가한 의존성
- 스프링부트 스타터 웹 : org.springframework.boot:spring-boot-starter-web
- 타임리프 : org.springframework.boot:spring-boot-starter-thymeleaf
- org.springframework.boot:spring-boot-starter-jdbc
- com.h2database:h2

## 구현 기능
### 어드민 페이지
- `GET /reservation`
- 화면에 html형식으로 보임
### 예약목록 조회
- `GET /reservations`
- json형식으로 실제 예약된 목록이 보임
### 예약 추가
- `POST /reservations`
### 예약 삭제
- `DELETE /reservations/{id}`
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
### 5단계
- JdbcTemplate을 이용하여 DataSource객체에 접근하기 
- DataSource 객체를 이용하여 Connection 확인하기 
- Connection 객체를 이용하여 데이터베이스 이름 검증 
- Connection 객체를 이용하여 테이블 이름 검증
### 6단계
- 경로 : /reservations에 POST요청 후 sql로 2차 검증
- 상태코드 : 200
### 7단계
- 1차
  - 경로 : /reservations에 POST요청하여 정상적으로 추가됐는지 검증
  - 상태코드 : 201
- 2차
  - 경로 : /reservations/1에 DELETE요청하여 정상적으로 제거됐는지 검증
  - 상태코드 : 204
---
### 검증범위
1. 루트의('/') GET 요청 검증
2. ReservationController의 /reservation의 GET요청 검증
3. ReservationController의 /reservations의 GET요청 검증
4. ReservationController의 /reservations의 POST요청 검증
5. ReservationController의 /reservations의 DELETE요청 검증
6. ReservationController의 /reservations POST의 예외처리 검증
7. ReservationController의 /reservations DELETE의 예외처리 검증
8. ReservationController의 /reservations POST처리 검증 
9. ReservationController의 /reservations DELETE처리 검증
