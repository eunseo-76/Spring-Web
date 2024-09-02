package com.kenny.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResolverController {

    @GetMapping("/string")
    public String stringResult(Model model) {
        // Model : view에서 표현되어야 하는 동적인 데이터를 담는 용도로 사용하는 객체
        // - 누가 로그인했는지에 따라 xxx님 환영합니다. 라는 응답 view 가 넘어와야 할 때,
        // xxx와 같이 응답 view에서 표현되어야 하는, 그때 그때 다른 객체.
        model.addAttribute("forwardMessage", "문자열로 뷰 이름 반환함...");

        // String 타입으로 리턴할 경우 논리적인 뷰 이름을 리턴한다.
        // ViewResolver가 prefix/suffix를 합쳐서 물리적인 뷰를 선택한다.
        return "result";    // controller에서 handler adapter로 반환하는 값.
        // handler adapter는 "result"라는 논리적인 뷰 이름을 dispatcher에서 반환한다.
        // 접두사 : /resources/templates (default)
        // 접미사 : .html
        // 이 둘을 합쳐서 실제 물리적인 뷰를 선택해서 view resolver가 어떤 view 인지 dispatcher에게 알려준다.

    }

    @GetMapping("/string-redirect")
    public String stringRedirect() {
        // redirect: 을 붙인 뒤 redirect 할 주소 값을 작성한다.
        return "redirect:/";  // forward 시 view 이름 리턴, redirect 시 "redirect:" 추가
    }

    /* Redirect 시 request scope의 데이터는 공유되지 않는다.
     * session scope에 너무 많은 데이터를 저장하는 것은 서버 성능에 영향을 준다.
     * RedirectAttribute 객체를 통해 잠시 세션에 저장했다가 redirect 후 세션에서 제거되게 할 수 있다.
     * */
    // 리다이렉트를 하면서도 데이터를 보존하는 방법
    @GetMapping("/string-redirect-attr")
    public String stringRedirectFlashAttribute(RedirectAttributes rttr) {
        rttr.addFlashAttribute("flashMessage1", "리다이렉트 attr 사용하여 redirect...");

        return "redirect:/";
    }


    @GetMapping("/modelandview")
    // 이전에는 String으로 논리적인 뷰를 전달했으나, 이번에는 ModelAndView
    // ModelAndView : Model + View 합친 객체
    public ModelAndView modelAndViewReturning(ModelAndView mv) {

        // Model 객체에 attribute 저장
        mv.addObject("forwardMessage", "ModelAndView를 이용한 모델과 뷰 반환");
        // View 객체에 논리적 뷰 이름 설정
        mv.setViewName("result");

        return mv;
    }

    @GetMapping("/modelandview-redirect")
    public ModelAndView modelAndViewRedirect(ModelAndView mv) {

        mv.setViewName("redirect:/");

        return mv;
    }

    @GetMapping("/modelandview-redirect-attr")
    public ModelAndView modelAndViewRedirectFlashAttribute(ModelAndView mv, RedirectAttributes rttr) {

        rttr.addFlashAttribute("flashMessage2", "리다이렉트 attr 사용하여 redirect...");
        mv.setViewName("redirect:/");

        return mv;
    }
}
