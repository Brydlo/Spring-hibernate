package sklep.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RootController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello world";
    }
    @GetMapping("/godzina")
    public String ktoraGodzina(Model model) {
        LocalDateTime  localDateTimeNow = LocalDateTime.now();
        model.addAttribute("dt", localDateTimeNow);
        return "pokaz_czas.jsp";
    }
}
