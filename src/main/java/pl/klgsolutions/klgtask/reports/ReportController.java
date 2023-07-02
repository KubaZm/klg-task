package pl.klgsolutions.klgtask.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/object")
    public String getObjectReport(Model model, @RequestParam String object, @RequestParam String from, @RequestParam String to) {
        model.addAttribute("data", reportService.getObjectReport(object, from, to));
        return "object-report";
    }

    @GetMapping("/landlords")
    public String getLandlordsReport(Model model, @RequestParam String from, @RequestParam String to) {
        model.addAttribute("data", reportService.getLandlordsReport(from, to));
        return "landlords-report";
    }
}
