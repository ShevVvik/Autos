package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import com.shevvvik.autos.services.OrdersLogic;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.services.validation.TransitionValidator;
import com.shevvvik.autos.web.forms.CommentForm;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TransitionValidator validator;

    @Autowired
    private OrdersLogic ordersLogic;


    @GetMapping("/orders/cancel/{id}")
    public String cancelOrder(Model model, @PathVariable String id) {
        if (!validator.checkTransitionToCancelled(Integer.valueOf(id))) {
            return "redirect:/orders/" + id + "/error";
        }
        ordersLogic.cancelOrder(id);
        return "redirect:/profile";
    }

    @GetMapping({"/orders/{id}", "/orders/{id}/{error}"})
    public String orderProfile(Model model,
                               @PathVariable String id,
                               @PathVariable(required = false) String error,
                               @ModelAttribute("commentForm") CommentForm commentForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        if(error != null && error.equals("error")) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
        }
        OrderProfile orderProfile = ordersLogic.getOrderProfile(id);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("order", orderProfile);
        model.addAttribute("role", role);
        return "/shared/orderProfile";
    }

    @GetMapping("/orders/{id}/assign")
    public String assignOrder(Model model, @PathVariable String id) {
        if (!validator.checkAssign(Integer.valueOf(id))) {
            return "redirect:/orders/" + id + "/error";
        };
        ordersLogic.assignOrder(id);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/orders/{id}/complete")
    public String completeOrder(Model model, @PathVariable String id) {
        if (!validator.checkTransitionToCompleted(Integer.valueOf(id))) {
            return "redirect:/orders/" + id + "/error";
        };
        ordersLogic.completeOrder(id);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/orders/{id}/start")
    public String startOrder(Model model, @PathVariable String id) {
        if (!validator.checkTransitionToInProgress(Integer.valueOf(id))) {
            return "redirect:/orders/" + id + "/error";
        };
        ordersLogic.startOrder(id);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/client/createOrder")
    public String createOrder(Model model, @ModelAttribute("orderForm") OrderForm orderForm) {
        ordersLogic.createOrder(orderForm);
        return "redirect:/profile";
    }

    @PostMapping("/orders/{id}/addComment")
    public String addComment(Model model, @ModelAttribute("commentForm") CommentForm commentForm, @PathVariable String id) {
        commentForm.setOrderId(Integer.valueOf(id));
        ordersLogic.addComment(commentForm);
        return "redirect:/profile";
    }


}
