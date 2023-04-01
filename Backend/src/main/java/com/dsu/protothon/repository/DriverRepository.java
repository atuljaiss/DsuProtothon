package com.codewarrior.travenjo.repository;

import com.codewarrior.travenjo.model.Driver;
import com.codewarrior.travenjo.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    @Query("select driver from Driver driver where driver.phoneNumber = ?1")
    Driver getByPhoneNumber(String phoneNumber);

    @Query("select driver from Driver driver where driver.driverId = ?1")
    Driver getByDriverId(Integer id);
}
