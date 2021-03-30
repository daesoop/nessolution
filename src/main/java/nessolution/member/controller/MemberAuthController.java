package nessolution.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class MemberAuthController {

    @GetMapping("/changePasswordForm")
    public String changeMemberPassword() {
        System.out.println("changepassword");
        return "/auth/changePassword";
    }

}
