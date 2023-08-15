package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertEquals(memberService.getMemberRepository(), memberRepository);
        assertEquals(orderService.getMemberRepository(), memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());

        // bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$68989ba6
        // @Configuration 어노테이션이 붙은 상황이면...
        // 스프링이 CGLIB 이라는 바이트 코드 관련 라이브러리를 이용하여 AppConfig 클래스를 상속받은
        // 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록
        // @Configuration 어노테이션이 없이 @Bean 어노테이션만 사용하면
        // CGLIB 기술 없이 순수한 AppConfig로 스프링 빈에 등록되지만 싱글톤은 보장되지 않는다.
    }
}
