package spring5fs.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring5fs.book.spring.DuplicateMemberException;
import spring5fs.book.spring.MemberRegisterService;
import spring5fs.book.spring.RegisterRequest;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private MemberRegisterService memberRegisterService;

    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "register/step1";
    }

    /**
     * @RequestParam value = HTTP 요청 파라미터 이름 지정
     * required = 값 필수 여부, true인데 값이 없으면 예외 발생 (기본값 true)
     * defaultValue = 값이 없을 때, 기본으로 지정할 값 설정 (기본값 없음)
     */
    @PostMapping("/register/step2")
    public String handleStep2(
            @RequestParam(value = "agree", defaultValue = "false") final Boolean agree,
            final RegisterRequest registerRequest) {
        if (!agree) {
            return "register/step1";
        }
        return "register/step2";
    }

    @GetMapping("/register/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @PostMapping("/register/step3")
    public String handleStep3(@Valid final RegisterRequest regReq, final Errors errors) {
        if (errors.hasErrors()) {
            return "register/step2";
        }

        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (final DuplicateMemberException ex) {
            return "register/step2";
        }
    }
}
