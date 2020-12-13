package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.services.OrdersLogic;
import com.shevvvik.autos.services.entities.ClientProfile;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrdersControllers {

    @Autowired
    private OrdersLogic ordersLogic;


    @GetMapping("/orders/cancel/{id}")
    public String cancelOrder(Model model, @PathVariable String id) {
        ordersLogic.cancelOrder(id);
        return "redirect:/profile";
    }

    @GetMapping("/orders/{id}")
    public String orderProfile(Model model, @PathVariable String id) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        OrderProfile orderProfile = new OrderProfile();
        orderProfile = ordersLogic.getOrderProfile(id);
        model.addAttribute("order", orderProfile);
        model.addAttribute("role", role);
        return "/shared/orderProfile";
    }

    @GetMapping("/orders/{id}/assign")
    public String assignOrder(Model model, @PathVariable String id) {
        ordersLogic.assignOrder(id);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/orders/{id}/complete")
    public String completeOrder(Model model, @PathVariable String id) {
        //ordersLogic.completeOrder(id);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/orders/{id}/start")
    public String startOrder(Model model, @PathVariable String id) {
        //ordersLogic.startOrder(id);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/client/createOrder")
    public String createOrder(Model model, @ModelAttribute("orderForm") OrderForm orderForm) {

        ordersLogic.createOrder(orderForm);
        return "redirect:/profile";
    }



}