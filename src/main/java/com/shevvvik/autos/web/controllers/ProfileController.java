package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.services.ClientsLogic;
import com.shevvvik.autos.services.DealersLogic;
import com.shevvvik.autos.services.entities.ClientProfile;
import com.shevvvik.autos.services.entities.DealerProfile;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private ClientsLogic service;

    @Autowired
    private DealersLogic dealersLogic;

    @GetMapping("/profile")
    public String profile(Model model, @ModelAttribute("orderForm") OrderForm orderForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        if ("ROLE_CLIENT".equals(role)) {
            ClientProfile clientProfile = service.getClientProfile();
            model.addAttribute("orderForm", orderForm);
            model.addAttribute("client", clientProfile);
            model.addAttribute("role", role);
            model.addAttribute("owner", true);
            return "/clients/profile";
        } else {
            DealerProfile dealerProfile = dealersLogic.getDealerProfile();
            model.addAttribute("dealer", dealerProfile);
            model.addAttribute("role", role);
            model.addAttribute("owner", true);
            return "/dealers/profile";
        }
    }

    @GetMapping("/clients/{id}")
    public String getClientById(Model model, @ModelAttribute("orderForm") OrderForm orderForm, @PathVariable String id) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        ClientProfile clientProfile = service.getClientProfileById(id);
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("client", clientProfile);
        model.addAttribute("role", role);
        model.addAttribute("owner", false);
        return "/clients/profile";
    }

}
