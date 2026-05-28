
# 방탈출 관리 애플리케이션

Spring Boot와 JdbcTemplate, H2 Database를 사용하여 방탈출 예약 정보를 관리하는 애플리케이션입니다.

## 기술 스택

- Java
- Spring Boot
- JdbcTemplate
- H2 Database
- Gradle

## 주요 기능

### 예약 관리

- 예약 페이지 조회
- 예약 목록 조회
- 예약 추가
- 예약 삭제

### 예약 시간 관리

- 예약 가능 시간 조회
- 예약 가능 시간 추가
- 예약 가능 시간 삭제

---

# API 명세

## 메인 페이지 조회

### Request

```http
GET /
````

### Response

```http
HTTP/1.1 200 OK
```

---

# 예약 API

## 예약 페이지 조회

### Request

```http
GET /reservation
```

### Response

```http
HTTP/1.1 200 OK
```

---

## 전체 예약 목록 조회

### Request

```http
GET /reservations
```

### Response

```http
HTTP/1.1 200 OK
Content-Type: application/json
```

```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-01-01",
    "time": "10:00"
  },
  {
    "id": 2,
    "name": "브라운",
    "date": "2023-01-02",
    "time": "11:00"
  }
]
```

---

## 예약 추가

### Request

```http
POST /reservations
Content-Type: application/json
```

```json
{
  "date": "2023-08-05",
  "name": "브라운",
  "time": "15:40"
}
```

### Response

```http
HTTP/1.1 201 Created
Location: /reservations/1
Content-Type: application/json
```

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

---

## 예약 삭제

### Request

```http
DELETE /reservations/{id}
```

### Response

```http
HTTP/1.1 204 No Content
```

---

# 예약 시간 API

## 예약 가능 시간 목록 조회

### Request

```http
GET /times
```

### Response

```http
HTTP/1.1 200 OK
Content-Type: application/json
```

```json
[
  {
    "id": 1,
    "time": "10:00"
  },
  {
    "id": 2,
    "time": "11:00"
  }
]
```

---

## 예약 가능 시간 추가

### Request

```http
POST /times
Content-Type: application/json
```

```json
{
  "time": "10:00"
}
```

### Response

```http
HTTP/1.1 201 Created
Location: /times/1
Content-Type: application/json
```

```json
{
  "id": 1,
  "time": "10:00"
}
```

---

## 예약 가능 시간 삭제

### Request

```http
DELETE /times/{id}
```

### Response

```http
HTTP/1.1 204 No Content
```

---
