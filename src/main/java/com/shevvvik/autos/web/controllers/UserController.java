package com.shevvvik.autos.web.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
/*
    @GetMapping("/profile")
    public String profile(Model model) {

        /*UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();*/
//        return "users/profile";
  //  }*/

    /*@GetMapping("/u/{userId}")
    public String index(Model model, @PathVariable Long userId) {

        UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User userX = userDet.getUser();
        User user = userService.getById(userId);

        model.addAttribute("user", user);
        model.addAttribute("userAut", userX);
        model.addAttribute("news", newsService.getNewsByAuthor(user.getId(), userX));
        model.addAttribute("friends", ((friendsService.isFriendsRequest(userX, user)) || (user.getId() == userX.getId())) ? true : false);
        model.addAttribute("otherUser", (user.equals(userX) || (userX.getHighLevelRole().equals(Role.ROLE_ADMIN))) ? false : true);
        model.addAttribute("createNews", (user.equals(userX)) ? false : true);

        return "/user/profile";
    }*/
}
