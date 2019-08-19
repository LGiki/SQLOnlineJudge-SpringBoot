package cn.edu.jmu.sqlonlinejudge.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }
}
