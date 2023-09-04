package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Proxy Object 는 요청이 오면 그 때 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
 * 클라이언트가 myLogger.logic() 을 호출하면 사실 Proxy Object 의 메서드를 호출한 것 이다.
 * Proxy Object 는 request 스코프의 실제 myLogger.logic() 을 호출한다.
 *
 * 특징정리
 * 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope를 사용할 수 있다.
 * 사실 Provider를 사용하든, 프록시를 사용하든 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 것이 핵심이다.
 * */
@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.log("controller test");
        Thread.sleep(100);
        logDemoService.logic("testId");
        return "ok";
    }
}
