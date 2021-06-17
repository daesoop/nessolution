package nessolution.member.service;


import nessolution.exception.CannotCreateUserException;
import nessolution.exception.UnAuthorizedException;
import nessolution.security.jwt.JwtTokenUtil;
import nessolution.security.jwt.JwtUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import nessolution.member.domain.ChangePasswordMember;
import nessolution.member.domain.Member;
import nessolution.member.domain.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MemberServiceSec {

    private static final Logger logger = getLogger(MemberServiceSec.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Autowired
    private MemberRepository memberRepository;

    public MemberServiceSec(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public JwtUser checkUserWithJWT(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    public Member createUser(String email, String password, String checkPassword, String phoneNumber) {
        logger.info("creteUser() 메소드 작동");
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        System.out.println("checkPassword : " + checkPassword);

        List<String> phoneList = new ArrayList();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < phoneNumber.length(); i++) {
            phoneList.add(String.valueOf(phoneNumber.charAt(i)));
        }

        for (int i = 0; i < phoneNumber.length(); i++) {
            try{
                sb.append(Integer.parseInt(phoneList.get(i)));
            } catch (NumberFormatException e) {
                System.out.print(phoneList.get(i));
            }
        }

        try{
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            throw new CannotCreateUserException("핸드폰 번호, 숫자 입력해주세요.");
        }

        Member member = new Member(email, new BCryptPasswordEncoder().encode(password), sb.toString());
        logger.info("create member test : " + member.getEmail() + " /// " + member.getPassword() + " /// " + member.getPhoneNumber()
        + " /// " + member.getRole() + " /// " + member.getEnabled() + " /// " + member.getLastPasswordResetDate());
        if (!member.checkBCryptPassword(checkPassword)) {
            throw new CannotCreateUserException("비밀번호가 일치하지 않습니다.");
        }
        try {
            return memberRepository.save(member);
        } catch(Throwable e) {
            throw new CannotCreateUserException("이미 존재한 이메일입니다. : " + e);
        }
    }

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(UnAuthorizedException::new);
    }

    //   비밀번호 변경
    public void changePassword(ChangePasswordMember changeMember) {
        logger.info("changePassword work");
        Member member = memberRepository.findByEmail(changeMember.getEmail()).orElseThrow(RuntimeException::new);
        if (member.checkBCryptPassword(changeMember.getPassword())) {
            logger.info("비밀번호 변경 서비스 동작");
            member.changePassword(changeMember.getChangedPassword());
        }
        memberRepository.save(member);
        logger.info(member.getPassword());
    }

    public String findEmail(String phoneNumber) {

        logger.info("findEmail 전");
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if (member == null) {
            return "찾으시는 사용자가 존재하지 않습니다. 입력 정보를 확인해주세요.";
        }
        logger.info("findEmail 후" + member.getEmail());
        return member.getEmail();
    }

    public String findPasword(String email, String phoneNumber) {
        try {
            Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
            try{
                Integer.parseInt(member.phoneNumber);
            } catch (NumberFormatException e) {
                return "핸드폰 번호 숫자를 입력해주세요.";
            }
            if (member.getPhoneNumber().equals(phoneNumber)) {
                String randomPassword = member.findPassword();
                memberRepository.save(member);
                return "임시 비밀번호 : " + randomPassword;
            } else {
                return "입력하신 정보가 틀렸습니다. 다시 확인해 주십시오.";
            }
        } catch (NoSuchElementException e) {
            return "찾으시는 사용자가 존재하지 않습니다. 입력 정보를 확인해주세요.";
        }
    }

    public String findRoleByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        return member.getRole();
    }
}
