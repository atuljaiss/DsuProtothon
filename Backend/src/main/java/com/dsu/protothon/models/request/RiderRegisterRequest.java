package com.dsu.protothon.model.request;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RiderRegisterRequest {

    private String phone;
    private List<String> services;
}
