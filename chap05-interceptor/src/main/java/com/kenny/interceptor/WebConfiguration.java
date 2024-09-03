package com.kenny.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // bean으로 등록
// WebMvcConfigurer 의 다양한 메소드 중 Interceptor 관련 메소드를 override 하여 interceptor를 추가한다.
public class WebConfiguration implements WebMvcConfigurer {

    private final StopWatchInterceptor stopWatchInterceptor;

    // 생성자를 통한 의존성 주입. @Autowired 생략
    public WebConfiguration(StopWatchInterceptor stopWatchInterceptor) {
        this.stopWatchInterceptor = stopWatchInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // InterceptorRegistry : Interceptor 종류를 관리하는 객체
        registry.addInterceptor(stopWatchInterceptor).addPathPatterns("/stopwatch");
        // stopwatch 요청이 있을 경우, registry.addInterceptor 인터셉터를 추가해라.
        // 스프링 컨테이너에 주입된 WebConfiguration bean과 StopWatchInterceptor bean 이므로 의존성 주입이 일어나게 해야 한다.
        // stopwatch... 를 생성자로 의존성 주입을 받아 추가했다.

    }
}
