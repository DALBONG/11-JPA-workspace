package jpabook.jpashop2.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    //Logger log = LoggerFactory.getLogger(getClass());
    // -> @Slf4j 하면 자동으로 생성해줌.
    // -> 디버깅을 위해 sout 찍는 것 처럼 sout 같은 기능을 함.

    @RequestMapping("/")
    public String Home(){
        log.info("home controller");
        return "home";
    }
}
