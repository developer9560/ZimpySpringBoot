package com.zimpy.util;

import com.zimpy.user.dto.AddressResponse;
import com.zimpy.user.entity.Address;

public class AddressMapper {
    public static AddressResponse entityToResponse(Address address){
        AddressResponse addressResponse = new AddressResponse(address.getId(), address.getAddressLine1(), address.getAddressLine2(), address.getCity(), address.getPostalCode(), address.getType(), address.isDefault(), address.getState(), address.getCountry());
        return  addressResponse;
    }
}
