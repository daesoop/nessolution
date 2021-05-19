package nessolution;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import nessolution.Product.dao.ProductRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MainController {
    private static final Logger logger = getLogger(MainController.class);

//    @Autowired
//    private ProductRepository productRepository;

    @GetMapping("/hello")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        logger.info("=====hello=====");
        return "redirect:/toHello";
    }

    @GetMapping("/toHello")
    public ModelAndView hello() {
        logger.info("/toHello 작동 완료");
        ModelAndView mv = new ModelAndView();
        mv.setStatus(HttpStatus.OK);
        mv.setViewName("/auth/hello");
        return mv;
    }

    @GetMapping("/customLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("/logout 작동 완료");
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home() {
        System.out.println("HOME 동작");
        return "/home";
    }

    @GetMapping("/index")
    public String index() {
        System.out.println("index test");
        return "/index";
    }
}
