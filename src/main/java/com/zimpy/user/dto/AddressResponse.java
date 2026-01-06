package com.zimpy.user.dto;

import com.zimpy.user.entity.Type;

public class AddressResponse {
    private Long id;
    private String addressLine1;
    private String getAddressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String type;
    private boolean isDefault;

    public AddressResponse(Long id, String addressLine1, String getAddressLine2, String city, String postalCode, Type type, boolean isDefault, String state, String country) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.getAddressLine2 = getAddressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.type = type;
        this.isDefault = isDefault;
        this.state = state;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getGetAddressLine2() {
        return getAddressLine2;
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

    public String getType() {
        return type;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
