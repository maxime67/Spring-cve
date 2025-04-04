package com.example.demo.controllers;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/follow/{cveId}")
    public ResponseEntity<String> followElement(@PathVariable String cveId) {
        userService.followCve(cveId);
        return ResponseEntity.ok("Followed");
    }

    @PostMapping("/unfollow/{cveId}")
    public ResponseEntity<String> unfollowElement(@PathVariable String cveId) {
        userService.unfollowCve(cveId);
        return ResponseEntity.ok("Unfollowed");
    }
}
