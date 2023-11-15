package com.scsb.t.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/env")
public class EnvController {
    @GetMapping("")
    public ResponseEntity<String> getFormData(@RequestParam String id) {
        try {
            String filePath = String.format("/templates/forms/config/%s.json", id);
            InputStream inputStream = getClass().getResourceAsStream(filePath);

            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder data = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                    // json return
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.toString());
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please Enter Correct Value");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please Enter Correct Value");
        }
    }
}
