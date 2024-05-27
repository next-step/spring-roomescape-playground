# 구현 과정 해결 사항
## 2단계
### 1. 컨트롤러와 서비스 간의 정보 교환
해당 문제에서 `Controller`에서 `List<Reservation>`을 가지도록 설계되었다.
원래 같으면 DTO를 이용하여 Controller와 Service 간 정보를 교환하지만, Controller에서 정보를 가지고 있기 때문에 이를 이용하기 까다로웠다.
따라서, 이번 문제에선 Service에서 만든 임시 데이터(List<Reservation)을 반환받아 Controller에서 복사하는 식으로 구현하였다.

### 2. 날짜 포맷
- Java 8 이전에는 `Date`를 이용하여 구현하였지만, Java 8 이후 부턴 `LocalDate`, `LocalTime`, `LocalDateTime` 을 이용한다.
- 날짜 포맷을 맞추기 위해 Reservation.class 에서 `@DateTimeFormat` 을 이용하여 포맷을 맞췄다.
```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate date;

@DateTimeFormat(pattern = "HH:mm")
private LocalTime times;
```
- 그리고, `String`을 `LocalDate`나 `LocalTime`으로 Parsing하기 위해 `LocalDate.parse()` 을 이용하였다.

<br/>

## 3단계
### 1. Create(URI)에서 URI는 무엇인가?
- `created`는 **새로운 자원이 정상적으로 생성되었다는 것을 의미**한다.
- 생성된 자원에 접근할 수 있도록 식별자가 포함된 URI를 반환한다.
```java
// 테스트 코드 중 일부
.header("Location", "/reservations/1")

// 컨트롤러 중 일부
return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
```
- Header의 `Location`은 redirect URL이면 이를 매핑하는 컨트롤러를 만들면 작동을 하는가? (해당 URL의 HTTP Method는 무엇인가?)

## 4단계
### 1. Map<String, String> params의 Validation 처리
- @RequestBody로 받은 `Map<String, String> params`의 경우 `@Valid`를 사용할 수 없다.
- 따라서, 이를 일급 컬렉션으로 Wrapping 후, Validation Annotation을 활용하여 처리가 가능한지 궁금하다.
- 이번 코드에선 Validation을 처리하는 클래스를 만들어, 해당 static 메서드를 통해 Exception을 호출하도록 설계하였다.
https://zoetechlog.tistory.com/m/116