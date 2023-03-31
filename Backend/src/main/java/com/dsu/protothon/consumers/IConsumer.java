package com.codewarrior.travenjo.consumers;

import com.codewarrior.travenjo.model.Driver;
import com.github.javafaker.Faker;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public interface IConsumer {

    default Driver getDriver(String phoneNumber) {
        Random rand = new Random();
        Faker faker = new Faker();
        String[] carModels= {"Honda", "KIA", " Jeep", "TOYOTA", "MARUTI", "TATA", "NISSAN", "NEXA", "BRAND2","ROYCE"};
        String[] name= {"Ayush Rungta", "Ayush B", "Atul Kumar", "Adarsh Kisor", "Swami"};
        String[] address= {"Kaglipura", "J P Nagar", "Jayanagar", "H S R Layout", "B T M Layout"};

        return Driver.builder()
                .phoneNumber(phoneNumber)
                .name(String.valueOf(name[rand.nextInt(5)]))
                .address(String.valueOf(address[rand.nextInt(5)]))
                .carModel(String.valueOf(Array.get(carModels,rand.nextInt(10))))
                .driverRegistrationIdWithServiceProvider(UUID.randomUUID().toString())
                .carRegistrationNumber("KA"+rand.nextInt(999) + " " + rand.nextInt(9999))
                .build();
    }

    default List<Driver> getAvailableDrivers(String pickUpLocation, String dropLocation) {
        return null;
    }

    default Double getPrice(String pickUpLocation, String dropLocation) {
        Random rand = new Random();
        return 100 + (1000 - 100) * rand.nextDouble();
    }
}
