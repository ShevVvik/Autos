package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import com.shevvvik.autos.services.DealersLogic;
import com.shevvvik.autos.services.entities.DealerProfile;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DealerController {

    @Autowired
    private DealersLogic dealersLogic;

    @GetMapping("/dealers/{id}")
    public String getDealer(Model model, @PathVariable String id) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        DealerProfile dealerProfile = dealersLogic.getDealerById(id);
        model.addAttribute("dealer", dealerProfile);
        model.addAttribute("role", role);
        model.addAttribute("owner", true);
        return "/dealers/profile";
    }

}
