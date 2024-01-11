package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        /*
         * 아래의 코드는 한 개의 memberService 가 여러 개로 생성되고 있다.
         * 사용자가 더 많아지거나, 초 단위로 계속 새로운 인스턴스를 생성하게 되면 메모리를 굉장히 많이 잡아먹게 된다.
         * 그래서 싱글톤 패턴을 이용하여 하나의 memberService 를 여러 클라이언트가 공유하는 방식이 효율적이다.
         * */

        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 하나의 인스턴스를 생성하기 때문에 인스턴스의 주소값이 같다. -> 메모리 절약
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        assertThat(singletonService1).isSameAs(singletonService2);
        // same ==
        // equal 오버라이드한 equal를 비교하는 것. same과 다르다.

        /*
         * 이와 같은 싱글톤을 일일이 하나씩 getInstance를 만들어줘야 하는 것이 아니라
         * 스프링 컨테이너를 쓰면 스프링 컨테이너가 모든 객체를 싱글톤 패턴으로 만들어 준다.
         * 뿐만 아니라, 스프링 컨테이너는 싱글톤 패턴의 문제점들을 해결하면서 객체 인스턴스를 싱글톤 1개만으로 운영해 준다.
         * */
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회 : 호출할 때마다 스프링 컨테이너에 bean 등록된 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회 : 호출할 때마다 스프링 컨테이너에 bean 등록된 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
        // 스프링의 기본 빈 등록 방식은 싱글톤이지만, 요청할 때 마다 새로운 객체를 생성하여
        // 반환하는 기능도 제공한다. 좌우지간, 이 챕터에서는 스프링은 싱글톤 기능을 제공한다는게 큰 장점이다.
    }
}
