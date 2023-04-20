package spring5fs.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring5fs.book.spring.DuplicateMemberException;
import spring5fs.book.spring.Member;
import spring5fs.book.spring.MemberDao;
import spring5fs.book.spring.MemberNotFoundException;
import spring5fs.book.spring.MemberRegisterService;
import spring5fs.book.spring.RegisterRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class RestMemberController {

    private MemberDao memberDao;
    private MemberRegisterService memberRegisterService;

    @GetMapping("/api/members")
    public List<Member> members() {
        return memberDao.selectAll();
    }

    @GetMapping("/api/members/{id}")
    public ResponseEntity<Object> member(@PathVariable final Long id, HttpServletResponse response) throws IOException {
        final Member member = memberDao.selectById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
        }
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/api/members")
    public ResponseEntity<Object> newMember(@RequestBody @Valid final RegisterRequest regReq,
                                            final HttpServletResponse response) throws IOException {
        try {
            final Long newMemberId = memberRegisterService.regist(regReq);
            final URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch (final DuplicateMemberException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Autowired
    public void setMemberDao(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setMemberRegisterService(final MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }
}
