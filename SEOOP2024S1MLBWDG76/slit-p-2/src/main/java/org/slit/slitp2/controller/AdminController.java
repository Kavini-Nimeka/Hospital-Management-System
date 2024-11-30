package org.slit.slitp2.controller;

import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.Admin;
import org.slit.slitp2.persistance.AdminRepo;
import org.slit.slitp2.request.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-11 11:29 AM
 */


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Service
public class AdminController {
private final AdminRepo adminRepo;
    @PostMapping("auth")
    public String auth(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("UnAuthorize"));
        if (Objects.equals(password, admin.getPassword())) {
            return "authed";
        } else throw new RuntimeException("UnAuthorize");
    }
}
