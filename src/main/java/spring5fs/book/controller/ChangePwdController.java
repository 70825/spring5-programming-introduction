package spring5fs.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring5fs.book.spring.AuthInfo;
import spring5fs.book.spring.ChangePasswordService;
import spring5fs.book.spring.WrongIdPasswordException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

    private ChangePasswordService changePasswordService;

    @Autowired
    public void setChangePasswordService(final ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @GetMapping
    public String form(@ModelAttribute("command") final ChangePwdCommand pwdCmd) {
        return "edit/changePwdForm";
    }

    @PostMapping
    public String submit(@ModelAttribute("command") final ChangePwdCommand pwdCmd,
                         final Errors errors,
                         final HttpSession session) {
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if (errors.hasErrors()) {
            return "edit/changePwdForm";
        }
        final AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if(authInfo == null) {
            return "redirect:/login";
        }
        try {
            changePasswordService.changePassword(
                    authInfo.getEmail(),
                    pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword());
            System.out.println("변환 완료");
            return "edit/changePwd";
        } catch (final WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching.currentPassword");
            return "edit/changePwdForm";
        }
    }
}
