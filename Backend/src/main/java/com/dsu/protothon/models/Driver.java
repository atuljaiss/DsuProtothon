package com.dsu.protothon.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="driver")
public class Driver {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer driverId;
    private String name;
    private String address;
    private String carModel;
    private String phoneNumber;
    private String driverRegistrationIdWithServiceProvider;
    private String driverRegisteredWith;
    private String carRegistrationNumber;
    private String profilePic;
}
