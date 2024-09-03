package com.kenny.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("/controller-null")
    public String nullPointerExceptionTest() {
        String str = null;
        System.out.println(str.charAt(0));  // 의도적으로 NullPointerException 발생시키는 코드

        return "";
    }

    @GetMapping("/controller-user")
    public String userExceptionTest() throws MemberRegistException {    // throw 하면 반드시 throws 해 주어야 함
        boolean check = true;
        if (check) throw new MemberRegistException("당신 같은 사람은 회원으로 받을 수 없습니다."); // 의도적으로 에러 발생시키는 코드
        return "/";
    }


    // 지역적으로만 exception handling이 가능하다 (다른 클래스에 있으면 exception handling이 안 된다)
    @ExceptionHandler(NullPointerException.class)
    // nullpointerexception 타입의 exception이 발생하면 이 메소드에서 처리하겠다(이 메소드로 trap 된다)
    public String nullPointerExceptionHandler(NullPointerException e) {
        System.out.println("특정 Controller 범위의 Exception Handler 동작");
        System.out.println("message : " + e.getMessage());

        return "error/nullPointer";
    }


    @ExceptionHandler(MemberRegistException.class)
    public String memberRegistExceptionHandler(MemberRegistException e, Model model) {

        System.out.println("특정 Controller 범위의 Exception Handler 동작");
        model.addAttribute("exception", e); // model 객체에 exception 담기
        // ui에서 활용할 model에 저장했으므로 화면(memgerRegist.html)에서 꺼내서 활용 가능

        return "error/memberRegist";
    }
}
