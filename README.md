# 방탈출 어드민 애플리케이션

## 1단계 & 2단계 요구사항

- [x] localhost:8080 요청 시 어드민 메인 페이지 응답
- [x] `/reservation` 요청 시 예약 관리 페이지 응답 
- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.
  - ```http request
    GET /reservations HTTP/1.1
    ```
  - ```json
    // HTTP/1.1 200 
    // Content-Type: application/json
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
