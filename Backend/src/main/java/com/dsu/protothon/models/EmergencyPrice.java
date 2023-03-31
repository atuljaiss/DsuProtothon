package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmergencyPrice {
    String driverId;
    String serviceProvider;
    String carModel;
    Double price;
    String carNumber;
}