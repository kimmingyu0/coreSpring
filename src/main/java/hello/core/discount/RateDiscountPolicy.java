package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
//@Qualifier("mainDiscountPolicy")
/** @Autowired 시 구분자 추가
 * @Qualifier 끼리 매칭
 * @Qualifier("mainDiscountPolicy")를 못 찾으면 mainDiscountPolicy 라는 이름의 스프링 빈을 추가로 찾는다.
 */
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        }else {
            return 0;
        }
    }
}
