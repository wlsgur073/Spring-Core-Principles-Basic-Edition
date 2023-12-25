package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 현재 MemberServiceImpl는 추상화인 MemberRepository와 구현체인 MemoryMemberRepository 모두 의존하고 있다.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //  MemberServiceImpl 의 생성자 주입을 통해서 인터페이스인 MemberRepository 만 의존한다.
    private final MemberRepository memberRepository;

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
}
