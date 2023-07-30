import hello.core.domain.Grade;
import hello.core.domain.Member;
import hello.core.domain.MemberService;
import hello.core.domain.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "김민규", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println(findMember);
    }
}
