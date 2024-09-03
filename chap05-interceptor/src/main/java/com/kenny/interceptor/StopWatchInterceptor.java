package com.kenny.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/* HandlerInterceptor 인터페이스를 구현한 클래스로 작성 */
// interceptor를 implement 하여 원하는 기능을 만든다.
@Component  // bean 등록
public class StopWatchInterceptor implements HandlerInterceptor {

    /* Interceptor는 스프링 컨테이너에 존재하는 반을 의존성 주입 받을 수 있다. */
    private final MenuService menuService;

    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    /* 전처리 메소드 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler 메소드 호출");

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;    // 컨트롤러 핸들러 메소드의 호출로 이어짐, false이면 호출하지 않음
        // false 이면 D.S ----> Controller로 가지 않도록 막을 수 있음.
        // 더 진행할지 말지에 대한 선택 가능
    }

    /* 후처리 메소드 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandler 메소드 호출함");

        long endTime = System.currentTimeMillis();
        long startTime = (Long) request.getAttribute("startTime");

        modelAndView.addObject("interval", endTime - startTime);
    }
    // controller 에서 반환이 온다 : 모델에 값을 담고, 응답 뷰를 어떻게 할 지에 대한 정보가 돌아온다

    /* 뷰가 랜더링 된 이후 동작하는 메소드 */
    // postHandle 메소드 보다 더 나중에 동작함
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 파라미터로 exception이 넘어온다. 예외가 발생하면 후처리 메소드 postHandle은 동작할 수 없으나, afterCompletion은 예외가 발생해도 동작한다.
        System.out.println("afterCompletion 메소드 호출");
        menuService.method();
    }
}
