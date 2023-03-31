package com.codewarrior.travenjo.controller;

import com.codewarrior.travenjo.model.Driver;
import com.codewarrior.travenjo.model.Rider;
import com.codewarrior.travenjo.model.request.DriverRegisterRequest;
import com.codewarrior.travenjo.model.request.RiderRegisterRequest;
import com.codewarrior.travenjo.service.DriverService;
import com.codewarrior.travenjo.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping(value="driver/register", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> registerDriver(@RequestBody DriverRegisterRequest driverRegisterRequest) {
        try {
            return ResponseEntity.ok(driverService.register(driverRegisterRequest));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
