package com.kenny.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("/other-controller-null")
    public String nullPointerExceptionTest() {
        String str = null;
        System.out.println(str.charAt(0));  // 의도적으로 NullPointerException 발생시키는 코드

        return "";
    }

    @GetMapping("/other-controller-user")
    public String userExceptionTest() throws MemberRegistException {    // throw 하면 반드시 throws 해 주어야 함
        boolean check = true;
        if (check) throw new MemberRegistException("당신 같은 사람은 회원으로 받을 수 없습니다."); // 의도적으로 에러 발생시키는 코드
        return "/";
    }

    @GetMapping("/other-controller-array")
    public String arrayExceptionTest() {
        double[] array = new double[0];
        System.out.println(array[0]);

        return "/";
    }
}
