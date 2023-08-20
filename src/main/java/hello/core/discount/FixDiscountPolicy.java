package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fixDiscountPolicy") 
/** @Autowired 시 구분자 추가
 * @Qualifier 끼리 매칭
 * @Qualifier("fixDiscountPolicy")를 못 찾으면 fixDiscountPolicy 라는 이름의 스프링 빈을 추가로 찾는다.
 */
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}

