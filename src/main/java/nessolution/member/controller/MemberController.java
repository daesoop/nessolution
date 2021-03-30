package nessolution.member.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nessolution.member.service.MemberService;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = getLogger(MemberController.class);

    @Autowired
    private MemberService userService;

    public MemberController(MemberService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("로그인 페이지 테스트");
        return "/test";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        System.out.println("로그인 페이지");
        return "/login";
    }


    @GetMapping("/createUser")
    public String create() {
        return "/member/memberCreateForm";
    }


    @GetMapping("/findPasswordForm")
    public String findPassword() {
        return "/member/findPassword";
    }

    @GetMapping("/findEmailForm")
    public String findEmail() {
        return "/member/findEmail";
    }

}
