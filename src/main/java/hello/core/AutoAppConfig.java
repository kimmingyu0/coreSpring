package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
/**
 * basePackages = "hello.core.member" 탐색할 패키지의 시작 위치 지정 (여러개도 가능)
 * basePackageClasses = AutoAppConfig.class 기준 클래스가 속한 패키지 상위로 탐색
 * 범위 지정하지 않을시 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작위치가 된다.
 *
 * 권장 : 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는것이다.
 * 최근 스프링 부트도 이 방법을 기본으로 제공
 * */
public class AutoAppConfig {
    // 자동 등록된 빈 보다 수동 등록된 빈이 우선순위를 가져서
    // Override를 해주지만, SpringApplication 실행시 오류 발생한다.
    // 자동으로 bean override를 false로 설정해놨기 때문이다.
    // spring.main.allow-bean-definition-overriding=true 이 속성을 주면 bean override가 활성화 됨
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
