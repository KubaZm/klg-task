package pl.klgsolutions.klgtask.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    IndexService indexService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("data", indexService.getData());
        return "index";
    }
}
