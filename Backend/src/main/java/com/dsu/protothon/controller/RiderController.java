package com.codewarrior.travenjo.controller;

import com.codewarrior.travenjo.model.Rider;
import com.codewarrior.travenjo.model.request.RiderRegisterRequest;
import com.codewarrior.travenjo.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping(value="rider/register", produces= MediaType.APPLICATION_JSON_VALUE)
    public Rider addBookingRequest(@RequestBody RiderRegisterRequest riderRegisterRequest) {
        return riderService.register(riderRegisterRequest);
    }
}
