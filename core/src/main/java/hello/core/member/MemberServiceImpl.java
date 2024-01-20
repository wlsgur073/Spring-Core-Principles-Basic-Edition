package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    // 현재 MemberServiceImpl는 추상화인 MemberRepository와 구현체인 MemoryMemberRepository 모두 의존하고 있다.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //  MemberServiceImpl 의 생성자 주입을 통해서 인터페이스인 MemberRepository 만 의존한다.
    private final MemberRepository memberRepository;

    /*
    * 기존에 AppConfig는 bean으로 생성하면서 MemberRepository를 불러오는 것이 명시되어 있지만
    * AutoAppConfig는 바디에 아무것도 존재하지 않는데 어떻게 하면 MemberRepository에 의존관계를 주입할 수 있을까?
    * 방법은 @Autowired를 붙이는 것이다.
    * */
    @Autowired // 마치 ac.getBean(MemberRepository.class) 하는 것 마냥 들어가게 된다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
