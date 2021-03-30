package nessolution.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nessolution.member.domain.Member;
import nessolution.member.domain.MemberRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = userRepository.findByEmail(username).orElseThrow(RuntimeException::new);

        System.out.println("========== JwtUserDetailsService loadUserByUsername =========");
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {

            JwtUser jwt = JwtUserFactory.create(user);
            System.out.println("====== JwtUser create ====== : " + jwt);
            System.out.println("====== JwtUser create ====== : " + jwt.getUsername() + "/" + jwt.getPassword() + " / "
                + jwt.getLastPasswordResetDate() + "/" + jwt.getAuthorities() + "//" + jwt.getId());
            return jwt;
        }
    }
}
