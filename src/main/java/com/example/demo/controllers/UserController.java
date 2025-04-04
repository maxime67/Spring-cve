package com.example.demo.controllers;

import com.example.demo.entities.CVE;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
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
