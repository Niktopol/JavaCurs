package com.coffehouse.app.controller;

import com.coffehouse.app.model.User;
import com.coffehouse.app.model.dto.UserDTO;
import com.coffehouse.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.coffehouse.app.model.User.Role.WORKER;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    FindByIndexNameSessionRepository<? extends Session> sessions;

    @PostMapping("/worker/add")
    public String addWorker(@RequestBody UserDTO workedData){
        User worker = new User(workedData.getName(), workedData.getUsername(), passwordEncoder.encode(workedData.getPassword()), WORKER, false);
        userRepository.save(worker);
        return "worked added";
    }
    @PostMapping("/worker/enable/{id}")
    public String enableWorker(@PathVariable Long id){
        User worker = userRepository.findById(id).get();
        worker.setEnabled(true);
        userRepository.save(worker);
        return "worked enabled";
    }
    @PostMapping("/worker/disable/{id}")
    public String disableWorker(@PathVariable Long id){
        User worker = userRepository.findById(id).get();
        worker.setEnabled(false);
        userRepository.save(worker);
        Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(worker.getUsername()).values();
        for (Session i: usersSessions){
            sessions.deleteById(i.getId());
        }
        return "worked disabled";
    }
}
