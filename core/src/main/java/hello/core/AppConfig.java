package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*
    * AppConfig 는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입 해준다.
    * */

    /*
    * call AppConfig.memberService
    * call AppConfig.memberRepository
    * call AppConfig.orderService
    * call AppConfig.memberRepository
    * call AppConfig.memberRepository
    *
    * 스프링을 실행하면 위처럼 출력될 것 같지만 실제로는 각 1번씩만 출력된다.
    * call AppConfig.memberService
    * call AppConfig.memberRepository
    * call AppConfig.orderService
    * */

//    나중에 배우겠지만, Autowired를 사용하면 스프링 컨테이너에 등록된 인스턴스를 넣어주기에
//    @Configuration 이 선언되지 않아도 같은 memberRepository를 호출해서 넣어준다.
//    @Autowired
//    MemberRepository memberRepository;

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
