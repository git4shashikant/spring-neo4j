package com.example.springneo4j.service;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;

import java.util.List;

public interface AddressService {
    Address saveAddress(Address address);
    Address addResident(String addressId, String personId);
}
