package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 데이터 베이스 커넥션풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
 * 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이 필요하다.
 *
 * 간단하게 외부 네트워크에 미리 연결하는 객체를 하나 생성한다고 가정해보자.
 * 실제로 네트워크에 연결하는건 아니고, 단순히 문자만 출력하도록 했다.
 * 'NetworkClient'는 애플리케이션 시작 시점에 'Connect()'를 호출해서 연결을 맺어두여야하고,
 * 애플리케이션이 종료되면 'disConnect()'를 호출해서 연결을 끊어야 한다.
 *
 * 스프링 빈은 간단하게 다음과 같은 라이프 사이클을 가진다.
 * '객체생성' -> '의존관계 주입'
 *
 * 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.
 * 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
 *
 * 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
 * 또한, 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다. 따라서 안전하게 종료 작업을 진행할 수 있다.
 * 
 * 스프링 빈의 이벤트 라이프사이클
 * '스프링 컨테이너 생성' -> '스프링 빈 생성' -> '의존관계 주입' -> '초기화 콜백' -> '사용' -> '소멸전 콜백' -> '스프링 종료'
 *
 * 참고 : 객체의 생성과 초기화를 분리하자.
 * 생성자는 필수 정보를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면에 초기화는 이렇게 생성된 값들을 활용해서
 * 외부 커넥션을 연결하는등 무거운 동작을 수행한다.
 * 따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화하는 부분을 명확하게 나누는 것이
 * 유지보수 관점에서 좋다.
 *
 * */
/**
 * 초기화,소멸 (InitializingBean, DisposableBean) 인터페이스의 단점
 * 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
 * 외부 라이브러리에 적용할 수 없다.
 * */
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
