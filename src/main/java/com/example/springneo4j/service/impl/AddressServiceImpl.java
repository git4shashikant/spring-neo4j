package com.example.springneo4j.service.impl;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;
import com.example.springneo4j.repository.AddressRepository;
import com.example.springneo4j.repository.PersonRepository;
import com.example.springneo4j.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address addResident(String addressId, String personId) {
        Address addressEntity = null;
        Person personEntity;

        Optional<Address> addressEntityOptional = addressRepository.findById(Long.parseLong(addressId));
        Optional<Person> personEntityOptional = personRepository.findById(Long.parseLong(personId));

        if (personEntityOptional.isPresent() && addressEntityOptional.isPresent()) {
            addressEntity = addressEntityOptional.get();
            personEntity = personEntityOptional.get();

            addressEntity.addResident(personEntity);
            personEntity.livesAt(addressEntity);

            addressRepository.save(addressEntity);
            personRepository.save(personEntity);
        }

        return addressEntity;
    }
}
