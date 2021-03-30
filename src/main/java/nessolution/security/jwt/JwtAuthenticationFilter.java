package nessolution.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = getLogger(JwtAuthenticationFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final String tokenHeader;
    private String requestHeader = null;

    public JwtAuthenticationFilter(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());
        logger.info("===== filter doFilterInternal ===== ");

        Cookie[] getCookie = request.getCookies();
        logger.info("Authorization test 2 : " + request.getHeader("authorization"));
        requestHeader = request.getHeader("authorization");
//        logger.info("쿠키 테스트 : " + getCookie[0].getValue());
//        logger.info("content type : " + request.getHeader("content-type"));
//        if (!request.getHeader("authorization").isEmpty()) {
//            requestHeader = request.getHeader("jwtToken");
//            logger.info("header jwt 토큰 성공");
//        }
        if (getCookie != null) {
            for (int i = 0; i < getCookie.length; i++) {
                if ("jwtToken".equals(getCookie[i].getName())) {
                    this.requestHeader = getCookie[i].getValue();
                }
                logger.info("==========token : " + getCookie[i].getValue());
            }
        }


        logger.info("requestHeader : " + this.requestHeader);
        String username = null;
        String authToken = null;
        if (this.requestHeader != null && this.requestHeader.startsWith("Bearer")) {
            authToken = requestHeader.substring(6);
            System.out.println("Filter의 authToken = " + authToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        logger.debug("checking authentication for user '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("security context was null, so authorizing user");


            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }


            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authorized user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
        this.requestHeader = null;
        System.out.println("==== dofilter 의 끝==== : ");
    }
}
