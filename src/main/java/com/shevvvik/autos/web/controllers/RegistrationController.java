package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.services.ServiceUtils;
import com.shevvvik.autos.services.UserDomainServices;
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

/*
    @Autowired
    private UserRegistrationFormValidator userValidator;*/

 /*   @InitBinder("userForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }*/

    @GetMapping("registration")
    public String registration(Model model, RegistrationForm userForm) {
        model.addAttribute("cities", serviceUtils.getCities());
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @PostMapping("registration")
    public String registrationPost(Model model, @ModelAttribute("userForm") RegistrationForm userForm) {
        /*if(binding.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "registration";
        }*/
        //UtilsForImage.saveImages(files, userForm.getLogin());
        //userService.createUserFromRegistrationForm(userForm);
        userDomainServices.createUser(userForm);
        return "redirect:/login";
    }

}
