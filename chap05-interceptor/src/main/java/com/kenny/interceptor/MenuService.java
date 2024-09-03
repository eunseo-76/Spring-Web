package com.kenny.interceptor;

import org.springframework.stereotype.Service;

@Service    // bean 등록
public class MenuService {
    public void method() {
        System.out.println("MenuService 클래스의 method 호출");
    }
}
