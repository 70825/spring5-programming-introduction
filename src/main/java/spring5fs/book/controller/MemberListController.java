package spring5fs.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring5fs.book.spring.Member;
import spring5fs.book.spring.MemberDao;

import java.util.List;

@Controller
public class MemberListController {

    private MemberDao memberDao;

    @Autowired
    public void setMemberDao(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @RequestMapping("/members")
    public String list(@ModelAttribute("cmd") final ListCommand listCommand, final Errors errors, final Model model) {
        if (errors.hasErrors()) {
            return "member/memberList";
        }

        if (listCommand.getFrom() != null && listCommand.getTo() != null) {
            final List<Member> members = memberDao.selectByRegdate(listCommand.getFrom(), listCommand.getTo());
            model.addAttribute("members", members);
        }
        return "member/memberList";
    }
}
