package com.example.demo;

import com.example.demo.entities.Cpe;
import com.example.demo.entities.Cve;
import com.example.demo.entities.Technology;
import com.example.demo.entities.User;
import com.example.demo.repositories.CpeDAO;
import com.example.demo.repositories.CveDAO;
import com.example.demo.repositories.TechnologyDAO;
import com.example.demo.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.demo.security","com.example.demo.services"})
public class Demo1Application {

    @Bean
    CommandLineRunner runner(CpeDAO cpeDAO, CveDAO cveDAO,UserDAO userRepository, TechnologyDAO technologyDAO) {
        return args -> {
            /*
            CVE
             */
            Instant instantCreated = Instant.parse("2025-04-02T21:38:52.798" + "Z");
            Instant instantModified = Instant.parse("2024-11-21T00:31:15.697" + "Z");
            Cve cve = new Cve();
            cve.setCveId("CVE-2007-2627");
            cve.setCreated_at(Date.from(instantCreated));
            cve.setTitle("CVE-2007-2627");
            cve.setDescription("Cross-site scripting (XSS) vulnerability in sidebar.php in WordPress, when custom 404 pages that call get_sidebar are used, allows remote attackers to inject arbitrary web script or HTML via the query string (PHP_SELF), a different vulnerability than CVE-2007-1622.");
            cve.setScore(6.0F);
            cve.setUpdate_at((Date.from(instantModified)));
            System.out.println(cve);
            cveDAO.save(cve);

             /*
            CPE
             */
            Cpe cpe = new Cpe();
            cpe.setCpeName("cpe:2.3:a:wordpress:wordpress:6.3:*:*:*:*:*:*:");
            cpe.setCveList(new ArrayList<>());
            cpe.addCve(cve);
            cpeDAO.save(cpe);


             /*
            technology
             */
            Technology technology = new Technology();
            technology.setName("WordPress");
            technology.setCpeList(new ArrayList<>());
            technology.addCpe(cpe);
            technologyDAO.save(technology);

            /*
            User
             */
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Smith");
            user.setUsername("kolizeton");
            user.setEmail("john.smith@gmail.com");
            user.setPassword("$2y$10$dnLsDJKFnl9qkiiCGesdDuNA4F8YMa2uEe92p0.riRvcDs.rM29.K");
            user.setPhoneNumber("06");
            user.setRoles("USER");
            user.addTechnology(technology);
            userRepository.save(user);
            Optional<User> saved = userRepository.findById(user.getUserId());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

}
