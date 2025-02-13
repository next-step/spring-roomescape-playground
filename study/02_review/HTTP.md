# HTTP
![Image](https://github.com/user-attachments/assets/d835d695-cf18-42d8-8327-b7fbf18c1513)

<img src="https://github.com/user-attachments/assets/d835d695-cf18-42d8-8327-b7fbf18c1513" width="300">

2 번째 코드 리뷰에서 받은 질문이였습니다. 결론적으로 어설프게 알고 답변을 했습니다. 

- RestController 에서는 표준화된 응답을 반환한다. 
- 표준화된 응답을 반환하지만, ResponseEntity를 같이 사용한 이유는 무엇인가?
- 표준화된 HTTP 응답을 내린다는 것은 무엇을 의미했나?

## 1. RestController

```java

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController
```
> **It's a convenient annotation that combines** @Controller and @ResponseBody

@RestController는 모든 요청의 반환을 객체로 자동으로 직렬화하여 HttpResponse로 응답합니다.
HttpResponse는 클라이언트의 HTTP 요청의 서버의 응답 메시지입니다. HTTP response 에는 status codes가 존재합니다.
이는 HTTP 요청이 성공적으로 완료되었는지 여부를 나타냅니다. 
1. Informational response (100 ~ 199)
2. Successful response (200 ~ 299)
3. Redirection message (300 ~ 399)
4. Client error response (400 ~ 499)
5. Server error response (500 ~ 599)

이를 이어서 생각해보면 RestController는 HttpResponse를 반환하고 HttpResponse는 상태코드를 가진다는 것까지 알았습니다.
그럼 응답을 어떻게 하는 것인지 살펴보겠습니다.

**응답**
![Image](https://github.com/user-attachments/assets/5bc00336-e90c-4599-bd6d-dc082668c6a1)

이와 같은 응답을 클라이언트와 서버간의 웹상에서 정보를 주고받을 수 있는 프로토콜로 HTTP라고 한다. 
또한 응답 메시지는 다음으로 구성된다.
- 상태표시 행: 상태코드와 reason message를 포함한다. (HTTP/1.1 200 OK)
- 응답 헤더 필드 (Content-Type:text/html)
- 빈줄
- 기타 메시지 (body에 정보를 담아서 응답한다.)

정리해보면 @RestController에서 반환하는 객체는 HttpResponse를 반환하고 객체를 직렬화하여
Http 응답의 바디에 담아서 응답합니다. 이로서 RestController가 일관된 방식으로 응답할 수 있다는 것을 알 수 있습니다.
또한, 기본적으로 응답 본문만 처리하고, **상태 코드와 헤더는 디폴트 값이 적용됩니다.**

## 2. 표준화된 응답을 반환하지만, ResponseEntity를 같이 사용한 이유는 무엇인가?

앞서 살펴봤던 `RestController` 정보와 취합해 보면 `ResponseEntity`를 사용하는 이유는 명확하집니다. 기본 반환 방식에서는 상태 코드나 헤더를 조정할 수 없으니 이를 해결하기 위해서
`ResponseEntity`를 사용합니다!!

## 3.표준화된 HTTP 응답을 내린다는 것은 무엇을 의미했나?
클라이언트와 서버 간의 정보를 요청 응답하기 위해서는 서로 정해진 약속에 맞게 데이터를 가공해서 보내야합니다. 데이터를 
요청 응답하기 위한 형식을 HTTP라고 합니다. 그렇다면 표준화된 응답을 내린다는 것은 무엇을 의미할까요?

만약 개발자가 자신의 마음대로 HTTP 응답을 반환한다면 일관성이 없어지고, 같이 협업하는 사람들 간의 혼란을 야기합니다.
특히 백엔드와 프론트엔드를 나누어서 개발하는 경우에는 혼란이 더 커질것입니다. 이에 HTTP 규약에 맞는 서로 이해가능하고 
약속된 방법으로 응답해야한다는 것을 의미합니다.

출처 : [Annotation Interface RestController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)
[HTTP response status codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
<br/>

[ResponseEntity - Spring Boot에서 Response를 만들자](https://tecoble.techcourse.co.kr/post/2021-05-10-response-entity/)
