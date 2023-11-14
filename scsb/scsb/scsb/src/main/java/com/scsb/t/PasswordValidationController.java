package com.scsb.t;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordValidationController {

    @GetMapping("/validatePassword")
    public String validatePassword(@RequestParam String password) {
        String generatedPassword = "5926ecd6-f538-4990-9585-0544b7e9da96";

        if (password.equals(generatedPassword)) {
            return "Password is valid!";
        } else {
            return "Password is invalid!";
        }
    }
}
