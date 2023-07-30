package hello.core.order;

import hello.core.domain.Grade;
import hello.core.domain.Member;
import hello.core.domain.MemberService;
import hello.core.domain.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 100000);
        Assertions.assertEquals(order.getDiscountPrice(), 1000);
    }
}
