package com.kenny.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* 클래스 레벨에도 @RequestMapping 사용이 가능하다.
 * URL을 공통 부분을 이용해서 설정하고 나면 매번 핸들러 메소드의 URL에 중복 내용을 작성하지 않아도 된다. */
@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {

    @GetMapping("/regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "GET 방식의 주문 등록용 핸들러 메소드 호출");
        return "mappingResult";
    }

    /* 여러 개의 패턴 매핑 */
    // 요청 주소가 여러 개 있는 경우 매핑하는 방법. 중괄호를 이용해 요청 주소를 나열한다. /order/modify, /order/delete 요청이 왔을 때 매핑
    @RequestMapping(value = {"/modify", "/delete"}, method = RequestMethod.POST)
    public String modifyAndDeleteOrder(Model model) {
        model.addAttribute("message", "POST 방식의 주문 수정, 삭제 공통 핸들러 메소드 호출");
        return "mappingResult";
    }

    // url에 리소스를 표시하는 고유 아이디(ex. /order/10)를 넣어 작성할 수 있다.
    // 이전에는 /order?orderId=10 이런 식으로 요청이 넘어왔다면,
    // getParameter("orderId") 이렇게 꺼내면 10이 반환이 되었음.
    // 그러나 /order/10은 쿼리 스트링이 아님. 경로에 변수가 들어간 형태이다.

    /* path variable : 요청 주소에 포함된 변수 */

    // /order/detail/10 과 같은 요청이 발생했을 때, 10을 꺼내오는 메소드
    // 중괄호 안에 변수명을 작성하고, @ PathVariable 어노테이션을 통해 해당 위치의 값을 가져옴
    @GetMapping("/detail/{orderNo}")
    public String selectOneOrderDetail(Model model, @PathVariable("orderNo") String orderNo) {
        model.addAttribute("message", orderNo + "번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    /* 아무런 URL을 설정하지 않으면 요청 처리에 대한 핸들러 메소드가 준비되지 않았을 때 해당 메소드를 매핑한다. */
    // /order/hello 이런 요청이 들어왔을 때, 원래는 매핑할 게 없으므로 404
    // 아래 메소드를 작성한 경우, 매핑이 된다.
    @RequestMapping
    public String otherRequest(Model model) {

        model.addAttribute("message", "order 요청이긴 하지만 다른 기능은 아직 준비되지 않음");
        return "mappingResult";
    }
}
