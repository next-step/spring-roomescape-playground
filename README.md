# roomescape

방탈출 게임 예약을 관리하는 어드민 웹 애플리케이션입니다.

## 실행 방법

### 서버 실행

```bash
./gradlew bootRun
```

서버 실행 후 `http://localhost:8080`으로 접속합니다.


### 테스트 실행

```bash
./gradlew test
```

## 기능 목록

### 1단계 - 홈 화면

- [x] `GET /` 요청 시 200 응답
- [x] `GET /` 요청 시 `templates/home.html` 렌더링

### 2단계 - 예약 조회

- [x] `GET /reservation` 요청 시 200 응답
- [x] `GET /reservation` 요청 시 `templates/reservation.html` 렌더링
- [x] `GET /reservations` 요청 시 200 응답
- [x] `GET /reservations` 요청 시 `Content-Type: application/json` 응답
- [x] `GET /reservations` 요청 시 예약 목록을 JSON 배열로 응답

## API 명세

### 예약 목록 조회

**Request**

```
GET /reservations HTTP/1.1
```

**Response**

```
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00:00"
    },
    {
        "id": 3,
        "name": "브라운",
        "date": "2023-01-03",
        "time": "12:00:00"
    }
]
```

**참고**: 미션 문서의 원래 API 명세는 `time` 필드가 `"10:00"` (시:분) 형태였으나, 제가 구현한 것에서의 응답은 `"10:00:00"` (시:분:초) 형태로 명세와 다릅니다.
