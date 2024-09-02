package com.kenny.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* DispathcerServlet 은 웹 요청을 받는 즉시 @Controller가 달린 컨트롤러 클래스에 처리를 위임한다.
* 그 과정은 컨트롤러 클래스의 핸들러 메서드에 선언된 다양한 @RequestMapping 설정 내용에 따른다.
* */
// 모든 요청은 디스패처 서블릿이 받음. 일단 받고 나서, @controller가 달린 애한테 넘김. 누구한테 위임할까는 @request mapping 설정 내용에 따름

/* 1. 요청 메소드 미지정 (메소드 방식과 무관하게 매핑한다) */
// index.html에 작성한 get, post 메소드 둘 다 매핑이 된다.
@Controller // 이 클래스는 controller의 역할을 할 것이라는 의미. 이 어노테이션으로 인해 bean으로 등록된다.
public class MethodMappingTestController {

    // index.html에 작성한 /menu/regist 요청
    @RequestMapping("/menu/regist")
    public String menuRegist(Model model) {

        /* Model 객체에 addAttribyte 메서드를 이용해 key, value를 추가하면
         * view에서 사용할 수 있다. -> chap03-view-resolver에서 다시 다룸 */
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        /* 반환하고자 하는 view의 경로를 포함한 이름을 작성한다.
         * resources/templates 하위부터의 경로를 작성한다. -> chap03-view-resolver에서 다시 다룸 */
        return "mappingResult";
    }

    /* 2. 요청 메소드 지정 */
    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String menuModify(Model model) {

        model.addAttribute("message", "GET 방식의 메뉴 수정용 핸들러 메소드 호출");

        return "mappingResult";
    }

    /* 3.  요청 메소드 전용 어노테이션 */
    // 위의 메소드의 간편화 버전
    @GetMapping("/menu/delete")
    public String getMenuDelete(Model model) {

        model.addAttribute("message", "GET 방식의 메뉴 삭제용 핸들러 메소드 호출");

        return "mappingResult";
    }


    @PostMapping("/menu/delete")
    public String postmenuDelete(Model model) {

        model.addAttribute("message", "POST 방식의 메뉴 삭제용 핸들러 메소드 호출");

        return "mappingResult";
    }

}

// @controller 하위에 @requestmapping 이라는 메소드를 만들 수 있음.
// 해당하는 요청과 메소드를 매핑하겠다는 의미.
// 잘 매핑되었는지 확인하기 위해 파라미터에 model를 받는다.

// model 객체에 addattribute를 하면 key, value 값을 view에서(응답할 화면) 사용할 수 있게 된다.

// 요청을 받아 응답해주어야 함. 응답할 경로를 return에 작성한다. 단, resources/templates 밑의 경로를 쓴다. templates

// 버튼을 눌러 get 요청이 들어오면 html 메소드에 결과가 응답되는 것을 테스트.

// @controller를 통해 bean 등록
// @requestmapping으로