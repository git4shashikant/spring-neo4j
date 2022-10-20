package com.example.springneo4j.service;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;

import java.util.Set;

public interface AddressService {
    Address saveAddress(Address address);
    Address addResident(Long addressId, Long personId);
    Set<Person> findResidents(Long addressId);
}
