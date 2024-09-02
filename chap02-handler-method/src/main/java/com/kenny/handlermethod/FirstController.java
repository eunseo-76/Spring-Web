package com.kenny.handlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequestMapping("/first/*")
public class FirstController {

    // GET /first/regist 요청
    @GetMapping("/regist")
    public void regist() {

    }

    /* 핸들러 메서드에 파라미터로 특정 몇 가지 타입을 선언하게 되면 핸들러 메서드 호출 시 인자로 값을 전달해준다. */


    // chap01에서는 사용자가 응답할 화면 경로를 String으로 리턴했음
    // void로 작성한다는 것은, 요청 주소값이 즉 응답 view 라는 의미
    /* 핸들러 메소드의 반환 값을 void로 설정하면 요청 주소가 곧 view의 이름이 된다.
     * => first/regist 뷰를 응답한다.
     * => resources/templates/first/regist.html 파일을 만들어서 작업한다.
     * */

    /* 1. WebRequest로 요청 파라미터 전달 받기
     * HttpServletRequest/Response, ServletRequest/ServletResponse도 핸들러 메소드 매개변수로 사용 가능하지만
     * WebRequest가 Servlet에 종속적이지 않으므로 Spring 기반의 프로젝트에서 더 자주 사용된다. */
    @PostMapping("/regist") // form action="regist" 상대 주소 이므로 /first/regist와 똑같은 주소에 대한 post 요청 매핑 처리
    public String registMenu(WebRequest request, Model model) {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price
                + "원으로 등록하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    // 파라미터에 HttpServletRequest, HttpServletRespose, ServletRequest, ServletResponse를 직접 받을 수 있으나 권장 x
    // 서블릿에 종속적인 코드이므로 권장 x
    // 스프링에서 제공하는 WebRequest 타입 사용

    @GetMapping("/modify")
    public void modify() {
    }

    /* 2. @RequestParam으로 요청 파라미터 전달 받기
     * 요청 파라미터를 매핑하여 핸들러 메소드 호출 시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다.
     * name 속성과 매개변수 명이 다른 경우 @RequestParam("name")으로 설정한다. (어노테이션 생략도 가능하다.)
     *
     * 전달하는 name 속성과 일치하는 것이 없는 경우 400 에러가 발생하는데 이는 required 속성의 기본값이 true이기 때문이다.
     * 해당 속성을 false로 설정하면 값이 존재하지 않을 경우 null 처리가 된다.
     *
     * 값을 입력하지 않고 넘기면 "" 빈 문자열이 요청으로 넘어오는데 이 때 parsing 관련 에러가 날 수 있다.
     * defaultValue 속성을 이용하면 기본 값 설정이 가능하다.
     * */
    @PostMapping("/modify")
    public String modifyMenu(
            @RequestParam(value = "nam", required = false) String modifyName,
            @RequestParam(value = "pric", defaultValue = "0") int modifyPrice,
            // 여기서 "nam" 이런 식으로 바꾸면 400 에러가 발생함 -> required=false 를 추가하면 에러가 안 나고 null로 바뀜
            // 이를 통해 꼭 필요하지 않은 param 이라고 처리할 수 있다.

            // http 통신을 통해 클라 -> 서버로 전달되는 값은 모두 문자열. 이를 필요에 따라 파싱해서 쓰는 것.
            //
            Model model) {
        String message = modifyName + " 메뉴의 가격을 " + modifyPrice + " 원으로 변경하였습니다.";
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    /* 파라미터가 여러 개인 경우 Map으로 한 번에 처리할 수 있다. Map의 key는 name 속성이 된다. */
    @PostMapping("/modifyAll")
    public String modifyAllMenu(@RequestParam Map<String, String> parameters, Model model) {

        String name = parameters.get("name");
        int price = Integer.parseInt(parameters.get("price"));

        String message = name + " 메뉴의 가격을 " + price + "원으로 변경하였습니다.";
        model.addAttribute("message", message);

        return "/first/messagePrinter";
    }

    // /first/search 뷰로 나타내겠다.
    @GetMapping("/search")
    public void search(Model model) {

    }
}

// mapping된 handler 메소드로 입력된 데이터를 전달하는 실습

// 서블릿에서는 서블릿 메소드로 request와 response가 파라미터로 넘어옴 (ex. doGet(req, res))
// req.getParameter(" ")로 넘어온 데이터를 꺼냈음.
// spring에서는 서블릿이 눈에 보이지 않음.