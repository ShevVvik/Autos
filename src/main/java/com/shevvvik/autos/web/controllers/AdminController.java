package com.shevvvik.autos.web.controllers;


import com.shevvvik.autos.services.AdminService;
import com.shevvvik.autos.services.logger.LogEntity;
import com.shevvvik.autos.web.forms.LoggerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    private AdminService service;

    @GetMapping("/admin/logs")
    public String searchDealers(Model model, LoggerForm logForms) {
        model.addAttribute("result", new ArrayList<LogEntity>());
        model.addAttribute("logForms", logForms);
        return "/admins/adminPage";
    }

    @PostMapping("/admin/logs")
    public String searchDealersPost(Model model, @ModelAttribute("logForms") LoggerForm logForms) {
        model.addAttribute("result", service.getLogEvents(logForms));
        model.addAttribute("logForms", logForms);
        return "/admins/adminPage";
    }

}
