package com.zimpy.user.Services;

import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.user.dto.AddressRequest;
import com.zimpy.user.dto.AddressResponse;
import com.zimpy.user.entity.Address;
import com.zimpy.user.entity.Type;
import com.zimpy.user.entity.User;
import com.zimpy.user.repository.AddressRepository;
import com.zimpy.user.repository.UserRepository;
import com.zimpy.util.AddressMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service

public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressResponse addAddress(Long userId, AddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with this id " + userId));
        if (request.isDefault()) {
            // addressRepository.findByUserId(userId)
            // .forEach(address -> {
            // address.setDefault(false);
            // addressRepository.save(address);
            // });
            addressRepository.clearDefaultAddress(userId);
        }
        Address address = new Address();
        address.setUser(user);
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setDefault(request.isDefault());
        address.setType(request.getType());
        address.setContactNumber(request.getContactNumber());
        addressRepository.save(address);

        AddressResponse addressResponse = AddressMapper.entityToResponse(address);
        return addressResponse;
    }

    public List<AddressResponse> getMyAddresses(Long userId) {
        List<Address> addressList = addressRepository.findByUserIdAndDeletedAtIsNull(userId);
        List<AddressResponse> responses = new ArrayList<>();
        for (Address address : addressList) {
            AddressResponse newAddress = AddressMapper.entityToResponse(address);
            responses.add(newAddress);
        }
        return responses;
    }

//    public List<AddressResponse> getMyActiveAddresses(Long userId){
//        List<Address> addressList = addressRepository.findByUserIdAndDeletedAtIsNull(userId);
//        List<AddressResponse> responses = new ArrayList<>();
//        for (Address address : addressList) {
//            AddressResponse newAddress = AddressMapper.entityToResponse(address);
//            responses.add(newAddress);
//        }
//        return responses;
//    }

    public AddressResponse updateAddress(
            Long userId,
            Long addressId,
            AddressRequest request) {
        Address address = addressRepository
                .findByIdAndUserIdAndDeletedAtIsNull(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (request.isDefault()) {
            addressRepository.setDefaultFalseForOthers(userId, addressId);
            address.setDefault(true);
        }

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setContactNumber(request.getContactNumber());
        address.setType(request.getType());

        addressRepository.save(address);

        return AddressMapper.entityToResponse(address);
    }

    public String deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserIdAndDeletedAtIsNull(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        boolean wasDefault = address.isDefault();
        // soft delete
        address.setDeletedAt(LocalDateTime.now());
        address.setDefault(false);
        addressRepository.save(address);

        if (wasDefault) {
            List<Address> otherAddress = addressRepository.findOtherActiveAddresses(userId, addressId);

            if (!otherAddress.isEmpty()) {
                Address newDefalut = otherAddress.get(0);
                newDefalut.setDefault(true);
                addressRepository.save(newDefalut);
            }
        }
        return "Successfully deleted";
    }

}
