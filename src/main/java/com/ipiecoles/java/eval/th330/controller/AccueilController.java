package com.ipiecoles.java.eval.th330.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccueilController {

    // Affiche la page d'accueil
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/"
    )
    public ModelAndView accueil() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }
}
