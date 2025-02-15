## toString 메소드 오버라이딩

**리뷰 내용**
> `toString`은 출력을 위한 일종의 뷰 로직인데 적절한지 고민해보면 좋을것 같아요

<br/>

## toString
<div align="center">
    <img src="/temp/images/toString/toString.png" alt="toString" style="width:800px; height:auto; object-fit:cover;">
</div>

<br/>

> It is recommended that all subclasses override this method

`Object` 객체를 상속받는 모든 객체들은 toString 메서드를 가지고 있습니다. `Oracle docs`에서는
서브 클래스는 모두 `toString`을 재정의하는 것을 추천하고 있습니다. 정의된 메서드는 인스턴스인
클래스의 이름과 `@` , 객체의 해시 코드의 부호 없는 16진수 표현으로 구성된 문자열을 반환합니다.

<br/>

### In POJO

<br/>

> From my experience I prefer to add a `toString()` method in all POJO classes because it helps if you want to print the current state of an instance, using a logging framework or not.

[이 글](https://medium.com/@nitinsridar/why-do-we-need-tostring-in-pojo-class-45dcdedfe9d8)을 쓴 사람은 POJO 클래스에서 `toString()`을 오버라이딩 하여 도움을 많이 받은 것 같다. 하지만,
같은 경험을 하지 않은 이상 내가 오버라이딩 하는 이유를 설명하지는 못하고 있습니다. 그래서 직접 유용할것 같은 이유를 찾아보았습니다.

<br/>

### Debug

프로그래밍을 하다보면 에러를 만나게 됩니다. 에러를 해결하기 위해서 의심가는 곳에 브레이크 포인트를 걸어 디버깅 모드로 실행해보며 이유를 찾습니다. `toString`을 오버라이딩 할 경우와 안한 경우의 차이점을 보겠습니다.

<br/>

**Not override `toString`**   
<div align="left">
    <img src="/temp/images/toString/debug.png" alt="debug-toString-no-override" style="width:500px; height:auto; object-fit:cover;">
</div>

<br/>

`toString`을 오버라이딩하지 않았을 경우, 디버그 모드에서 실행되는 클래스의 이름과 인스턴스 이름과 해쉬값을 통해서 값을 표현하고 있습니다.

<br/>

**Override `toString`**   
<div align="left">
    <img src="/temp/images/toString/debug-toString.png" alt="debug-toString-override" style="width:500px; height:auto; object-fit:cover;">
</div>

`toString`을 오버라이딩했을 경우, 디버그 모드에서 객체의 값을 직관적으로 볼 수 있습니다.

<br/>

### Logging

정보를 제공하는 일련의 기록인 log를 통해 시스템 관련 정보를 얻습니다. 로깅을 하는 경우에도 `toString`을 오버라이딩 안하였을 경우를 실제 결과를 살펴보겠습니다.

<br/>

**Not override toString**
<div align="left">
    <img src="/temp/images/toString/log.png" alt="log-toString-no-override" style="width:500px; height:auto; object-fit:cover;">
</div>

<br/>

디버그 모드에서 살펴본것과 동일하게 객체의 정보를 식별할 수 없습니다. 다음으로 오버라이딩한 후 로깅의 결과를 살펴보겠습니다.

<br/>

**Override toString**
<div align="left">
    <img src="/temp/images/toString/log-toString.png" alt="log-toString-override" style="width:500px; height:auto; object-fit:cover;">
</div>

<br/>

로깅을 하는 경우에도 디버그 모드와 동일하게 객체의 정보를 직관적으로 알 수 있습니다. 

## 생각해볼점
- toString 은 뷰 로직을 위한 것인가?
- 객체간의 참조가 양방향일 경우 순환 참조의 문제는 어떻게 해결할것인가?

toString 메소드는 뷰 로직 또는 책임인가에 대해서는 잘 모르겠지만, 스스로 생각하기에는 toString 을 통해서 객체의 정보를 표현하는 것은 적절한 책임이고 다른 곳에 전가하면 안된다고 생각을 하고 있습니다. 또한
디버깅의 목적에 맞도록 효과적으로 정보를 반환하도록 재정의할 때 고려해야합니다.

그리고 순환참조의 문제로 문제가 발생할 수 있습니다. 이를 해결하는 방법으로는 순환참조 필드를 toString 메서드에서 제외하는 것입니다. 
제외하는 방법으로는 정보를 반환하고 싶은 필드만 of 속성으로 제공하는 것입니다. 코드를 통해서 살펴보겠습니다.
```java
@ToString(of = {"name"})
class Foo {
    String name;
    int age;

    public Foo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
public static void main(String[] args) {
    Foo foo = new Foo("foo", 30);
    log.info("{}", foo);
}
```
출력의 결과는 다음과 같습니다.   
`14:12:05.208 [main] INFO roomescape.application.Main -- Main.Foo(name=foo)`

이를 통해 toString 재정의로 인해 순환참조의 문제를 해결할 수 있습니다. 

### 출처
- [Oracle-docs](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString--) - toString
- [why do we need toString](https://medium.com/@nitinsridar/why-do-we-need-tostring-in-pojo-class-45dcdedfe9d8) - POJO class
