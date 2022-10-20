package com.example.springneo4j.service.impl;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;
import com.example.springneo4j.repository.AddressRepository;
import com.example.springneo4j.repository.PersonRepository;
import com.example.springneo4j.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public Address addResident(Long addressId, Long personId) {
        Address addressEntity = null;
        Person personEntity;

        Optional<Address> addressEntityOptional = addressRepository.findById(addressId);
        Optional<Person> personEntityOptional = personRepository.findById(personId);

        if (personEntityOptional.isPresent() && addressEntityOptional.isPresent()) {
            addressEntity = addressEntityOptional.get();
            personEntity = personEntityOptional.get();

            //addressEntity.addResident(personEntity);
            personEntity.livesAt(addressEntity);

            //addressRepository.save(addressEntity);
            personRepository.save(personEntity);
        }

        return addressEntity;
    }

    @Override
    public Set<Person> findResidents(Long addressId) {
        Set<Person> residents = new HashSet<>();
        Optional<Address> addressEntityOptional = addressRepository.findById(addressId);
        addressEntityOptional.ifPresent(address -> residents.addAll(address.getResidents()));

        return residents;
    }
}
