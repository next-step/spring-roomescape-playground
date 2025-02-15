# Bulider Pattern

빌더 패턴을 언제 사용하는지에 대한 기준을 정리해보겠습니다.

## 어떤 문제를 해결하기 위해서 도입하여 문제를 해결할까?

`Builder` 패턴은 생성적 디자인 패턴으로 객체 생성과 관련된 문제를 해결합니다. 문제는 생성자에서 많은 매개변수로 객체를 생성할 수 있을 때 시작되며, 그 중 일부는 **필수 이고 다른 일부는 선택 사항** 일 수 있습니다. 객체를 생성하기 위해 오버로드된 생성자가 있는 경우 많은 생성자가 생길 수 있고 많은 매개변수를 사용해아할 때 문제를 해결할 수 있습니다.

문제 :

1. 유지 관리해야 할 생성자가 너무 많습니다.

2. 많은 필드가 동일한 유형의 타입을 가집니다.

3. 기본생성자로 생성한 다음 setter를 이용하여 객체를 빌드하는 동안 Object는 일관되지 않은 상태로 두는 문제가 발생합니다. (객체는 생성 시점에 완전한 상태여야 한다고 생각합니다.)


<br/>

## 빌더 패턴의 장단점
다음으로 장 단점을 살펴보겠습니다.

장점

1. 객체를 생성하는 데 필요한 필드 수가 4개나 5개보다 많은 경우 유지관리가 용이합니다.
2. 명시적인 메서드 호출을 통해 전달하는 내용을 알 수 있으므로 오류가 발생할 가능성이 적습니다.
3. 완전히 생성된 객체만 클라이언트에 제공되므로 안정적입니다.

단점

1. 복잡한 코드가 발생하고 필드를 복사해야하여 중복이 발생합니다.

<br/>

## 코드로 살펴보기

```java
public class Foo {

  private String name;
  private String password;
  private String address;
  private String someThing;
  private Long balance;

  public Foo(String name, String password, String address, String someThing, Long balance) {
    //...
  }

  public static void main(String[] args) {
    Foo foo = new Foo ("fooName", "passwordTest", "tempAddress", "testSomeThing", 1L);
  }
}
```

생성자를 호출할 때 매겨변수의 개수와 타입의 중복이 많아 휴먼 에러가 발생할 수 있습니다. 이는 컴파일러가 타입만 맞다면 필드에 맞는 값이 들어가 객체를 생성하는지 체크해주지 않습니다. 이 문제는 정적 팩토리 메서드를 사용하여도 해결되지 않는 문제입니다.

또한 생성 시에 필수로 입력되어야 하는 필드와 꼭 초기화 해주지 않아도 되는 필드가 존재할 수 있습니다. 빌더 패턴은 필수로 받아야 하는 필드를 컴파일러가 체크해서 코드를 작성하는 시점에 에러를 발견하여 디버깅할 수 있습니다.

<br/>

### 롬복 어노테이션 사용하여 필수값 정의하는 방법

```java
@Builder(builderMethodName = "innerBuilder")
public class Foo {

  private String name;
  private String password;
  private String address;
  private String someThing;
  private Long balance;

  public static FooBuilder builder(String name) {
    return innerBuilder.name(name);
  }
}
```

<br/>

## Builder 사용시 매개변수를 최소화 하자
```java
@Builder
public class Member {...}
```
클래스 위에 @Builder를 사용 시 @AllArgsConstructor 어노테이션을 붙인 효과를 발생시켜 모든 멤버 필드에 대해서 매개변수를 받는 기본 생성자를 만듭니다.

생성 시에 받지 않아야 할 데이터들이 클래스 상단 @Builder를 사용하게 되면 발생하게 됩니다. 이에 다음과 같이 생성자를 필요조건에 따라 지정하고 그 위에 @Builder를 붙이는게 바람직합니다.

많은 글들에서 말하길 각자 상황과 환경에 맞게 사용하는 것이 바람직하다고 말한다. 또한 다음의 문구가 인상이 깊었습니다.

> 제가 말하고 싶은 부분은 클린 코드나, 유지 보수하기 좋은 코드, 좋은 캡슐화 이런 것들이 너무 거창한 것이 아니라 객체를 생성할 때나 메서드를 제공할 때 조금 더 깊게 생각해 보는 습관이 중요하다고 생각합니다.

> 디자인 패턴을 배우기 전에 특정 디자인 패턴이 해결하는 문제를 찾는 것이 좋습니다. 문제에 직면하지 않고 디자인 패턴을 배우는 것은 그다지 효과적이지 않습니다. 대신 이미 문제에 직면했다면 디자인 패턴을 이해하고 문제를 해결하는 방법을 배우는 것이 훨씬 쉽습니다.

**출처**

- [Builder Design pattern in java - java code guide](<https://www.javacodegeeks.com/2012/07/builder-design-pattern-in-java.html#:~:text=Disadvantages%3A%201)%20verbose%20and%20code,4%20or%20at%20most%205.>)

- [객체 생성에 생성자 vs 빌더패턴](https://velog.io/@a01021039107/4.-%EA%B0%9D%EC%B2%B4-%EC%83%9D%EC%84%B1%EC%97%90-%EC%83%9D%EC%84%B1%EC%9E%90-vs-%EB%B9%8C%EB%8D%94-%ED%8C%A8%ED%84%B4-%EB%AC%B4%EC%97%87%EC%9D%84-%EC%8D%A8%EC%95%BC-%ED%95%A0%EA%B9%8C)

- [실무에서 Lombok 사용법법](https://cheese10yun.github.io/lombok/)
