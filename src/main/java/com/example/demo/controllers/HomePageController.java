package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.CveService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    @Autowired
    UserService userService;
    @Autowired
    CveService cveService;

    @GetMapping("/")
    public String user(Model model) {
        User user = userService.getAuthUser();
        model.addAttribute("user", user);

        model.addAttribute("cves", cveService.getAllCve());
        model.addAttribute("followedCves", userService.getFollowedCves());

        return "user";
    }
}
