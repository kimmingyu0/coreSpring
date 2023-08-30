package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
 * 그런데 싱글톤 빈은 생성시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만,
 * 싱글톤 빈과 함께 유지되는 것이 문제다.
 *
 * 프로토타입 빈을 주입 시점에만 새로 생성하는게 아니라, 사용할 때마다 새로 생성해서 사용하는 것을 원한다.
 *
 * 참고
 * 여러 빈에서 같은 프로토타입 빈을 주입받으면, 주입 받는 시점에 각각 새로운 프로토타입 빈이 생성된다.
 * */
public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertEquals(prototypeBean1.getCount(), 1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertEquals(prototypeBean2.getCount(), 1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertEquals(count1, 1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertEquals(count2, 1);
    }

    @Scope("singleton")
    static class ClientBean {
//        @Autowired
//        ApplicationContext context;
        /**
         * 주석 처리된 코드를 살펴보면 ApplicationContext를 주입받아서 Prototype 빈을 조회하는 식으로 되어있다.
         * 그런데 이렇게 스프링의 애플리케이션 컨텍스트 전체를 주입받게 되면,
         * 스프링 컨테이너에 종속적인 코드가 되고, 단위테스트가 어려워진다.
         *
         * 의존관계를 외부에서 주입(DI) 받는게 아니라 직접 필요한 의존관계를 찾는것을
         * Dependency Lookup (DL) 의존관계 조회라고 한다.
         * */
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
//            PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }


    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }
}
