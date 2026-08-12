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

## 테스트 결과
- 1단계 : 통과
- 2단계 : 통과
