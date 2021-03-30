package nessolution.member.controller;

import nessolution.member.service.MemberService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nessolution.member.domain.MemberDto;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/member")
public class ApiMemberController {

    private static final Logger logger = getLogger(ApiMemberController.class);

    private MemberService memberService;

    public ApiMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody MemberDto memberDto) {
         memberService.createUser(memberDto.getUsername(), memberDto.getPassword(), memberDto.getCheckPassword(), memberDto.getPhoneNumber());
        return new ResponseEntity(HttpStatus.OK);
    }
}
