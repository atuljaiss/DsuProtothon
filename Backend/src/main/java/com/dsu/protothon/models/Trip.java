package com.dsu.protothon.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="trip")
public class Trip {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String pickup;
    private String destination;
    private int driverId;
    private int riderId;
    private String status;
    private int nextTripId;
    private String tripType;
}
