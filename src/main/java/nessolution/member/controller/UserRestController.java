package nessolution.member.controller;

import nessolution.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import nessolution.security.jwt.JwtTokenUtil;

@RestController
public class UserRestController {

    private String tokenHeader = "Authorization";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @GetMapping("user")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        System.out.println("=====UserRestController===== : 요거 작동하는건가?");
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
