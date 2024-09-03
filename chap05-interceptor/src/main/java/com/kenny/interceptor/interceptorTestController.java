package com.kenny.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class interceptorTestController {

    @GetMapping("/stopwatch")
    public String stopwatch() throws InterruptedException {
        System.out.println("핸들러 메소드 호출함");
        Thread.sleep(1000);
        return "result";
    }
}
