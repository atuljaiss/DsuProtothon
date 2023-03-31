package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SplitPrice {
    String driverId;
    String serviceProvider;
    String carModel;
    Double price;
    String to;
    String from;
}
