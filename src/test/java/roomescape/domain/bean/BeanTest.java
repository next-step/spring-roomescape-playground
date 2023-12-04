package roomescape.domain.bean;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

public class BeanTest {

    @Test
    @DisplayName("StaticApplicationContext를 사용하여 코드 상에서 직접 Bean을 등록하고 관리한다.")
    public void StaticApplicationContext를_사용하여_코드_상에서_직접_Bean을_등록하고_관리한다() {
        // IoC 컨테이너 생성
        StaticApplicationContext context = new StaticApplicationContext();
        // Hello 클래스를 싱글톤 빈으로 컨테이너에 등록
        context.registerSingleton("hello", Hello.class);

        Hello hello = context.getBean("hello", Hello.class);
        assertThat(hello).isNotNull();
    }

    @Test
    @DisplayName("BeanDefinition을 사용하여 빈 메타 정보를 설정한다.")
    public void BeanDefinition을_사용하여_빈_메타_정보를_설정한다() {
        StaticApplicationContext context = new StaticApplicationContext();
        context.registerSingleton("hello1", Hello.class);

        Hello hello1 = context.getBean("hello1", Hello.class);
        assertThat(hello1).isNotNull();

        // 빈 메타정보를 담은 오프젝트 생성
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        // 빈 프로퍼티 설정
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        // 빈 메타정보를 hello2 라는 이름으로 컨테이너에 등록
        context.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = context.getBean("hello2", Hello.class);
        assertThat(hello2).isNotNull();
        assertThat(hello2.sayHello()).isEqualTo("Hello Spring");

        assertThat(hello1).isNotSameAs(hello2);
        assertThat(context.getBeanFactory().getBeanDefinitionCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Bean을 찾아서 DI할 수 있다.")
    public void Bean을_찾아서_DI할_수_있다() {
        StaticApplicationContext context = new StaticApplicationContext();
        //context.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class)); FIXME: StringPrinter가 제공되지 않음.

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        // 아이디가 printer 인 빈을 찾아서 printer 프로퍼티에 DI
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
        context.registerBeanDefinition("hello", helloDef);

        Hello hello = context.getBean("hello", Hello.class);
        hello.print();

        assertThat(context.getBean("printer").toString()).isEqualTo("Hello Spring");
    }
}
