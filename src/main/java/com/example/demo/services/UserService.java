package com.example.demo.services;

import com.example.demo.entities.CVE;
import com.example.demo.entities.Cpe;
import com.example.demo.entities.Technology;
import com.example.demo.entities.User;
import com.example.demo.repositories.CveDAO;
import com.example.demo.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    CveDAO cveDAO;
    public User findUserByUsername(String username) {
        if(userDAO.findByUsername(username).isPresent()) {
            return userDAO.findByUsername(username).get();
        } else {
            return null;
        }
    };
    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.findUserByUsername(username);
    }
    public List<CVE> getFollowedCves() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.findUserByUsername(username);
        return user.getFollowCveList();
    };
    public void saveUser(User user){
        userDAO.save(user);
    };
    public void followCve(String cveID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.findUserByUsername(username);
        CVE cve = cveDAO.findById(cveID).orElseThrow();

        if (!user.getFollowCveList().contains(cve)) {
            user.getFollowCveList().add(cve);
            userDAO.save(user);
        }
    }

    public void unfollowCve(String cveID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.findUserByUsername(username);
        CVE cve = cveDAO.findById(cveID).orElseThrow();

        user.getFollowCveList().remove(cve);
        userDAO.save(user);
    }

    public List<Technology> getFollowedTechnologies()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.findUserByUsername(username);
        return user.getTechnologyList();
    }
}
