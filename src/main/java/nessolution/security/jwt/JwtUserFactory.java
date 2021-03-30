package nessolution.security.jwt;

import nessolution.member.domain.Member;
import org.slf4j.Logger;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.slf4j.LoggerFactory.getLogger;

public final class JwtUserFactory {

    private static final Logger logger = getLogger(JwtUserFactory.class);

    private JwtUserFactory() {
    }

    public static JwtUser create(Member user) {
        JwtUser jwtUser = new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
        logger.info("====jwtUser create ==== : " + jwtUser.isAccountNonExpired() + " :// " + jwtUser.isCredentialsNonExpired());
        return jwtUser;
    }
}
