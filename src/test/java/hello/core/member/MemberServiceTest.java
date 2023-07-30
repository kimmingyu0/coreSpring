package hello.core.member;

import hello.core.AppConfig;
import hello.core.domain.Grade;
import hello.core.domain.Member;
import hello.core.domain.MemberService;
import hello.core.domain.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        //given
        Member member1 = new Member(1L,"김민규",Grade.VIP);
        //when
        memberService.join(member1);
        Member findMember = memberService.findMember(1L);
        //then
        Assertions.assertEquals(member1, findMember);
    }
}
