package nessolution.member.controller;

import nessolution.member.service.MemberServiceSec;
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

    private MemberServiceSec memberService;

    public ApiMemberController(MemberServiceSec memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody MemberDto memberDto) {
        System.out.println("------------------------");
        System.out.println("member name : " + memberDto.getUsername());
         memberService.createUser(memberDto.getUsername(), memberDto.getPassword(), memberDto.getCheckPassword(), memberDto.getPhoneNumber());
        return new ResponseEntity(HttpStatus.OK);
    }
}
