package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @Quilifier("mainDiscountPolicy") 이렇게 문자를 적으면 컴파일 시 타입 체크가 안된다.
 * 다음과 같이 애노테이션을 만들어서 문제를 해결할 수 있다.
 * */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
