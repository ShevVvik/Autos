package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.services.ServiceUtils;
import com.shevvvik.autos.services.UserDomainServices;
import com.shevvvik.autos.services.validation.UserFormValidation;
import com.shevvvik.autos.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    private UserDomainServices userDomainServices;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private UserFormValidation validation;

    @GetMapping("registration")
    public String registration(Model model, RegistrationForm userForm) {
        model.addAttribute("error", false);
        model.addAttribute("cities", serviceUtils.getCities());
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @PostMapping("registration")
    public String registrationPost(Model model, @ModelAttribute("userForm") RegistrationForm userForm) {
        if(!validation.checkRegistrationForm(userForm)) {
            model.addAttribute("error", true);
            model.addAttribute("cities", serviceUtils.getCities());
            model.addAttribute("userForm", userForm);
            return "registration";
        } else {
            userDomainServices.createUser(userForm);
            return "redirect:/login";
        }
    }

}
