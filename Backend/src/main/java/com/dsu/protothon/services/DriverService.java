package com.dsu.protothon.services;
import com.codewarrior.travenjo.consumers.ConsumerFactory;
import com.codewarrior.travenjo.consumers.IConsumer;
import com.codewarrior.travenjo.model.Driver;
import com.codewarrior.travenjo.model.Rider;
import com.codewarrior.travenjo.model.request.DriverRegisterRequest;
import com.codewarrior.travenjo.model.request.RiderRegisterRequest;
import com.codewarrior.travenjo.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository ;

    @Autowired
    private ConsumerFactory consumerFactory;

    public Driver register(DriverRegisterRequest driverRegisterRequest) throws Exception {

        Driver existingDriver = driverRepository.getByPhoneNumber(driverRegisterRequest.getPhone());
        if(existingDriver != null) return existingDriver;

        IConsumer consumer =
                consumerFactory.getConsumer(driverRegisterRequest.getRegisteredWith());
        Driver driver  = consumer.getDriver(driverRegisterRequest.getPhone());

        driver.setDriverRegisteredWith(driverRegisterRequest.getRegisteredWith());
        driver.setProfilePic(driverRegisterRequest.getProfilePic());

        return driverRepository.save(driver);
    }
}
