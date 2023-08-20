package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
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
    private final MemberRepository memberRepository;
    /* @Autowired */
    private final DiscountPolicy discountPolicy;

    /**
     * 생성자 주입
     * 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다.
     * '불변', '필수' 의존관계에 사용
     *
     * 참고
     * 생성자가 딱 하나일시 @Autowired 생략 가능.
     *
     * 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법이다.
     * 필드에 final 키워드 사용할 수 있다. 그래서 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.
     * 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
     * 생성자 주입과 수정자 주입을 동시에 사용할 수  있다.
     *
     * 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로,
     * 필드에 final 키워드를 사용할 수 없다.
     */
    
    /**
     * @Autowired 시 조회 대상 빈이 2개 이상일 때 해결방법
     * @Autowired 필드 명 매칭
     * -> 타입 매칭을 시도하고, 이 때 여러빈이 있으면 필드 이름, 파라미터 이름으로 추가 매칭한다.
     * @Quilifier -> @Quilifier 끼리 매칭 -> 빈 이름 매칭
     * -> 추가 구분자를 제공하는 것 일뿐. 빈 이름을 변경하는 것이 아니다.
     * @Primary 사용
     * -> 현재 DiscountPolicy 자료형의 빈이 FixDiscountPolicy, RateDiscountPolicy 두 종류가 있는데
     * @Primary 가 붙은 RateDiscountPolicy 가 우선 순위를 가져서 자동 주입됨.
     *
     * 우선 순위 (@Primary, @Quilifier)
     * @Primary 는 기본값 처럼 동작하는 것이고, @Quilifier 는 매우 상세하게 동작한다.
     * 스프링은 자동보다는 수동이, 넓은 범위의 선택권 보다는 좁은 범위의 선택권이 우선순위가 높다.
     * 따라서 @Quilifier 가 우선권이 높다.
     *
     * */
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * 수정자 주입
     *
     * '선택', '변경' 가능성이 있는 의존관계에 사용
     * 자바빈 프로퍼티의 규약의 수정자 메서드 방식을 사용하는 방법
     *
     * @Autowired의 기본동작은 주입할 대상이 없으면 오류가 발생한다.
     * 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정하면 된다.
     * */
//    @Autowired(required = false)
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

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
