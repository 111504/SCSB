package com.scsb.t.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/form")
public class FormController {

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> getFormData(@RequestParam String id) {
        try {
            String filePath = String.format("/templates/forms/%s.json", id);
            InputStream inputStream = getClass().getResourceAsStream(filePath);

            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            // json return
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please Enter Correct Value");
        }

    }
}