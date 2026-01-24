package com.zimpy.user.dto;

import com.zimpy.user.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressRequest {

    @NotBlank
    private String addressLine1;

    private String addressLine2;
    private String contactNumber;
    @NotNull
    private Type type;

    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;

    private boolean isDefault;


    public Type getType() {
        return type;
    }


    public String getAddressLine1() {
        return addressLine1;
    }



    public String getAddressLine2() {
        return addressLine2;
    }



    public String getCity() {
        return city;
    }



    public String getState() {
        return state;
    }



    public String getCountry() {
        return country;
    }



    public String getPostalCode() {
        return postalCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }

}
