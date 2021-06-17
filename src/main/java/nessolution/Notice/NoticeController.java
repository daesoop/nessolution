package nessolution.Notice;

import nessolution.security.jwt.JwtTokenUtil;
import nessolution.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private NoticeService noticeService;


    @PostMapping("")
    public ResponseEntity<Notice> create(@Valid @RequestBody NoticeDto noticeDto, HttpServletRequest request) {
        Notice notice = null;
        try {
            notice = noticeService.createNotice(noticeDto ,request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Notice>(notice, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Notice>(notice, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notice> update(@PathVariable long id, @RequestBody String comment) {
        Notice notice = noticeService.updateNotice(id, comment);
        return new ResponseEntity<Notice>(noticeService.updateNotice(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        // 공지사항 삭제 하기 전 delete 변수 확인 후 삭제
        noticeService.deleteNotice(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}