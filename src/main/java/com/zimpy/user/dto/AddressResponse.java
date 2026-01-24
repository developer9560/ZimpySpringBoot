package com.zimpy.user.dto;

import com.zimpy.user.entity.Type;

public class AddressResponse {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private Type type;
    private boolean isDefault;
    private String contactNumber;

    public AddressResponse(Long id,
                           String addressLine1,
                           String addressLine2,
                           String city,
                           String postalCode,
                           Type type,
                           boolean isDefault,
                           String state,
                           String country,
                           String contactNumber) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.type = type;
        this.isDefault = isDefault;
        this.state = state;
        this.country = country;
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Type getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
