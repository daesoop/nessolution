package nessolution.Notice;

import nessolution.exception.UnAuthorizedException;
import nessolution.member.domain.Member;
import nessolution.member.service.MemberServiceSec;
import nessolution.security.jwt.JwtTokenUtil;
import nessolution.security.jwt.JwtUser;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class NoticeService {


    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private MemberServiceSec memberService;

    public Notice createNotice(NoticeDto noticeDto, HttpServletRequest request) throws Exception {
        JwtUser user = memberService.checkUserWithJWT(request);
        boolean check = checkMemberRoleForNotice(user);
        if (check == false) {
            throw new UnAuthorizedException();
        }
        Notice notice = noticeRepository.save(noticeDto._toNotice());
        return notice;
    }

    public Notice updateNotice(long id, String comment) {
        Notice notice = noticeRepository.findById(id).orElseThrow(UnknownError::new);
        notice.update(comment);
        return noticeRepository.save(notice);
    }

    public void deleteNotice(long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(UnknownError::new);
        noticeRepository.delete(notice);
    }

    public boolean checkMemberRoleForNotice(JwtUser user) {
        String email = user.getUsername();
        String role = memberService.findRoleByEmail(email);
        if (role != "master") {
            return false;
        }
        return true;
    }
}