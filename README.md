# 요구사항

## 1단계 요구사항

- localhost:8080 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/home.html 파일을 이용하세요.

## 2단계 요구사항

- /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

## 3단계 요구사항

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.

## 4단계 요구사항

- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.
- 예를 들면 예약 추가 시 필요한 인자값이 비어있는 경우 혹은 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우가 있습니다.
- +) 저는 예약 하나를 1시간으로 잡고, 겹치지 않도록 처리했습니다.

## 5단계 요구사항

h2 데이터베이스를 활용하여 데이터를 저장하도록 수정하세요.

- build.gradle 파일을 이용하여 다음 두 의존성을 추가하세요.
    - ```spring-boot-stater-jdbc```
    - ```h2```

- 테이블 스키마 정의
    - 데이터베이스 테이블 생성을 위해 schema.sql 파일을 생성하고 테이블을 생성하는 쿼리를 작성하세요.
- 데이터베이스 설정
    - h2 데이터베이스의 console 기능을 활성화하세요.
    - datasource url을 다음과 같이 지정하세요.
        - ```jdbc:h2:mem:database```

## 6단계 요구사항

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.

## 7단계 요구사항

- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하세요.
- 기존에 사용하던 List 및 AtomicLong 을 제거하세요.
- 예약 관리 기능이 정상 동작하도록 기능을 완성하세요.

## 8단계 요구사항

- 방탈출 시간표가 정해져 있는데 직접 입력하기 번거로워서 선택하는 방식으로 수정하려합니다.
- API 명세를 따라 시간 관리 API를 구현하세요.
- 아래 화면에서 시간 관리 기능이 잘 동작해야합니다.

## 9단계 요구사항

- 기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정하세요.
- templates/reservation.html 대신 templates/new-reservation.html 파일을 활용하세요.

## 10단계 요구사항

- 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리를 해보세요.
- 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용해보세요.

# API 명세

#### 예약 조회 Request

```http
GET /reservations HTTP/1.1
```

#### 예약 조회 Response

```http
HTTP/1.1 200
Content-Type: application/json

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

#### 예약 추가 Request

```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

#### 예약 추가 Response

```http
HTTP/1.1 201
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

#### 예약 취소 Request

```http
DELETE /reservations/1 HTTP/1.1
```

#### 예약 취소 Response

```http
HTTP/1.1 204 No Content
```

#### 시간 추가 Request

```http
POST /times HTTP/1.1
content-type: application/json

{
    "time": "10:00"
}
```

#### 시간 추가 Response

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /times/1

{
    "id": 1,
    "time": "10:00"
}
```

#### 시간 조회 Request

```http
GET /times HTTP/1.1
```

#### 시간 조회 Response

```http
HTTP/1.1 200 
Content-Type: application/json

[
   {
    "id": 1,
    "time": "10:00"
    }
]
```

#### 시간 삭제 Request

```http
DELETE /times/1 HTTP/1.1
```

#### 시간 삭제 Response

```http
HTTP/1.1 204 No Content
```
