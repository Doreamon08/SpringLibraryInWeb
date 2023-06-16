package ru.doreamon08.SpringMVCTutor2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {
    private int result;
    private StringBuilder resultString;
    @GetMapping("/calculator")
    public String calculator(@RequestParam("a") String a,
                             @RequestParam("b") String b,
                             @RequestParam("action")String action,
                             Model model) {

        model.addAttribute("result", result);
        model.addAttribute("a", a);
        model.addAttribute("b", b);
        model.addAttribute("action", action);

        if (action.equals("multiplication")) {
            multiplication(Integer.parseInt(a), Integer.parseInt(b));
            String s = String.valueOf(result);
            model.addAttribute("result", result);
        }

        return "/calculator/calculator";
    }

    private void multiplication(int a, int b) {
        result = a * b;
    }
}
