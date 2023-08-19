package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    /**
     * 필드 주입
     *
     * 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 존재한다.
     * DI 프레임워크가 없다면 아무것도 할 수 없다.
     * 권장하지 않는다.
     * 
     * 테스트 파일이나 스프링 설정을 목적으로하는 @Configuration 같은곳에서만 사용할 것을 권장
     * */
    /* @Autowired */
    private MemberRepository memberRepository;
    /* @Autowired */
    private DiscountPolicy discountPolicy;

    /**
     * 생성자 주입
     * 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다.
     * '불변', '필수' 의존관계에 사용
     *
     * 생성자가 딱 하나일시 @Autowired 생략 가능.
     */
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    /**
     * 수정자 주입
     *
     * '선택', '변경' 가능성이 있는 의존관계에 사용
     * 자바빈 프로퍼티의 규약의 수정자 메서드 방식을 사용하는 방법
     *
     * @Autowired의 기본동작은 주입할 대상이 없으면 오류가 발생한다.
     * 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정하면 된다.
     * */
    @Autowired(required = false)
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice){
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
