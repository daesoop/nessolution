package nessolution.member.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nessolution.exception.AuthenticationException;
import nessolution.member.domain.ChangePasswordMember;
import nessolution.member.domain.FindMember;
import nessolution.member.service.MemberServiceSec;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ApiAuthenticationController {

    private static final Logger logger = getLogger(ApiAuthenticationController.class);

    @Autowired
    private MemberServiceSec memberService;

    private String tokenHeader  ;


    @PostMapping("/find/email")
    public ResponseEntity<String> findEmail(@RequestBody FindMember member) {

        logger.info("이메일 찾기 컨트롤러 " + member.getPhoneNumber());
        String email = memberService.findEmail(member.getPhoneNumber());
        logger.info(email);
        return new ResponseEntity<String>(email, HttpStatus.OK);
    }

    @PostMapping("/find/password")
    public ResponseEntity<String> findPassword(@RequestBody FindMember member) {
        logger.info("패스워드 찾기 컨트롤러" + member.getEmail() + " // " + member.getPhoneNumber());
        String password = memberService.findPasword(member.getEmail(), member.getPhoneNumber());
        return new ResponseEntity<String>(password, HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordMember member) {
        logger.info("비밀번호 변경 컨트롤러");
        logger.info("email : " + member.getEmail() + " // password : " + member.getPassword() + " //changePassword : " + member.getChangedPassword());
        memberService.changePassword(member);
        String url = "/hello";
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        logger.info("로그인 성공 후 컨트롤러로");
        RestTemplate restTemplate = new RestTemplate();
        String url = "/hello";
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }

//    @PostMapping("/loginUser")
//    public ResponseEntity<String> loginUser(HttpServletRequest request, HttpServletResponse response) {
//        logger.info("동작 완료!" + response.getHeaderNames());
//        return ResponseEntity.ok("/hello");
//    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}