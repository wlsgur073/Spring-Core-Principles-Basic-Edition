package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 현재 MemberServiceImpl는 추상화인 MemberRepository와 구현체인 MemoryMemberRepository 모두 의존하고 있다.
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
