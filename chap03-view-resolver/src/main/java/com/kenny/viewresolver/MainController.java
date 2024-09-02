package com.kenny.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller // controller를 bean으로 등록
public class MainController {

    @RequestMapping(value = {"/", "/main"}) // root or /main 요청을 하면 main.html로 연결시키겠다
    public String main() {
        return "main";  // view의 이름 <- view의 이름을 쓰는 건 forward 하는 것.
    }
}
