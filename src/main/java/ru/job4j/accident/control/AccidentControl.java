package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("rules", service.getRules());
        model.addAttribute("types", service.getAccidentTypes());
        return "accident/create";
    }

    @GetMapping("/update")
    public String update(@RequestParam int id, Model model) {
        model.addAttribute("accident", service.findAccidentById(id));
        model.addAttribute("rules", service.getRules());
        model.addAttribute("types", service.getAccidentTypes());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        service.saveOrUpdate(accident, rIds);
        return "redirect:/";
    }
}
