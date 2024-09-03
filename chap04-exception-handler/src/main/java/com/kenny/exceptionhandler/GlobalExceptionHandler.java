package com.kenny.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice : 해당 어노테이션이 적용된 클래스의 @ExceptionHandler는 전역적으로 기능한다 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    // nullpointerexception 타입의 exception이 발생하면 이 메소드에서 처리하겠다(이 메소드로 trap 된다)
    public String nullPointerExceptionHandler(NullPointerException e) {
        System.out.println("전역 범위의 Exception Handler 동작");
        System.out.println("message : " + e.getMessage());

        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String memberRegistExceptionHandler(MemberRegistException e, Model model) {
        System.out.println("전역 범위의 Exception Handler 동작");
        model.addAttribute("exception", e); // model 객체에 exception 담기
        // ui에서 활용할 model에 저장했으므로 화면(memgerRegist.html)에서 꺼내서 활용 가능

        return "error/memberRegist";
    }

    /* 상위 타입의 Exception을 통해 Handler를 작성하면 하위 타입의 모든 Exception을 처리할 수 있다. */
    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(Exception e) {

        return "error/default";
    }
}
