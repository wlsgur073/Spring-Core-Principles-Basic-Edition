package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        /*
        * 스프링은 모든 것이 ApplicationContext 에서 시작한다.
        * ApplicationContext 이 스프링 컨테이너라고 한다.
        * 어노테이션 기반으로 Configration의 bean들을 설정해주었기에 AnnotationConfigApplicationContext 를 생성해준다.
        * */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //  기본적으로 method의 이름으로 등록되어 있다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
