package com.dsu.protothon.services;

import com.codewarrior.travenjo.model.Rider;
import com.codewarrior.travenjo.model.request.RiderRegisterRequest;
import com.codewarrior.travenjo.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    public Rider register(RiderRegisterrequest riderRegisterrequest) {
        return riderRepository.save()
    }

    public Rider register(RiderRegisterRequest riderRegisterRequest) {
        return riderRepository.save(Rider
                .builder()
                .phoneNumber(riderRegisterRequest.getPhone())
                .preferredServices(String.join(",", riderRegisterRequest.getServices()))
                .build()
        );
    }


}
