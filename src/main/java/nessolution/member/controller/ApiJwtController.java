package nessolution.member.controller;

import nessolution.security.jwt.JwtAuthenticationRequest;
import nessolution.security.jwt.JwtTokenUtil;
import nessolution.security.jwt.JwtUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nessolution.exception.AuthenticationException;
import nessolution.security.jwt.JwtAuthenticationResponse;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ApiJwtController {

    private static final Logger logger = getLogger(ApiJwtController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping("/loginUser")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException {
        logger.info("패스워드 검사 : " + authenticationRequest.getPassword());
        logger.info("아이디 검사 : " + authenticationRequest.getUsername());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        String bearerToken = "Bearer" + token;
        response.setHeader("Authorization",  bearerToken);
        logger.info("========AuthenticationController ===========" + bearerToken);
        Cookie cookie = new Cookie("jwtToken", bearerToken); // 쿠키 이름을 name으로 생성

        cookie.setMaxAge(60 * 60 * 24); // 기간을 하루로 지정
        response.addCookie(cookie);

        // Return the token
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);
        return ResponseEntity.ok(jwtAuthenticationResponse.getToken());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        System.out.println("========AuthenticationController refresh ===========" + authToken);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        System.out.println("authenticate working : " + username + " / : " + password);
        JwtUser member = (JwtUser) userDetailsService.loadUserByUsername(username);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, member.getAuthorities()));
            System.out.println("authentication.getCredentials : " + authentication.getCredentials() + "/ principal : " + authentication.getPrincipal().toString());

        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
