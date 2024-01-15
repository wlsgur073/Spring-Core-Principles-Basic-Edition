package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    /*
    * AppConfig를 보면 같은 인스턴스를 두 번 호출하는 것 처럼 보인다.
    * 그러나 확인해보면 MemoryMemberRepository 인스턴스를 모두 같은 인스턴스로 공유되어 사용된다.
    * memberService -> new MemoryMemberRepository()
    * orderService -> new MemoryMemberRepository()
    * */


    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 3개 모두 같은 인스턴스
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println(" memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        // AnnotationConfigApplicationContext에 class를 인자로 넣어주면 해당 class도 spring bean에 등록이 된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        /*
        * 출력 값 : class hello.core.AppConfig$$SpringCGLIB$$0
        * 순수한 클래스라면 `class hello.core.AppConfig`이라고 출력되어야 한다.
        * 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은
        * 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
        *
        * @Configuration을 선언하지 않아도 컨텍스트에 등록된 AppConfig과 @bean들은 모두 스프링 컨테이너에 생성된다.
        * 하지만 @Configuration를 선언하지 않으면 처음에 기대했던 memberRepository가 3번 호출되며 서로 다른 인스턴스가 된다.
        * */
        AppConfig appConfig = new AppConfig();
        System.out.println("appConfig = " + appConfig.getClass());
        System.out.println("bean = " + bean.getClass());
    }
}
