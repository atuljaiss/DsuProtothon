//package com.Du.travenjo.model.request;

package com.dsu.protothon.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DriverRegisterRequest {

    private String phone;
    private String registeredWith;
    private String carRegistrationNumber;
    private String profilePic;

}
