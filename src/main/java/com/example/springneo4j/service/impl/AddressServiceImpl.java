package com.example.springneo4j.service.impl;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;
import com.example.springneo4j.repository.AddressRepository;
import com.example.springneo4j.repository.PersonRepository;
import com.example.springneo4j.service.AddressService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressServiceImpl implements AddressService {

    private final Driver driver;
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    @Autowired
    public AddressServiceImpl(Driver driver,
                              AddressRepository addressRepository,
                              PersonRepository personRepository) {
        this.driver = driver;
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

            addressEntity.addResident(personEntity);
            personEntity.livesAt(addressEntity);

            addressRepository.save(addressEntity);
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

    @Override
    public List<Map<String, Object>> findPersonFromSameCountry(String country) {
        List<Map<String, Object>> response = new ArrayList<>();
        try (var session = driver.session()) {
            var records = session.readTransaction(tx ->
                    tx.run("MATCH (p:Person) -[livesAt:LIVES_AT] ->(a:Address) where a.country = $country return p",
                            Values.parameters("country", country)).list());

            for (Record record : records) {
                for (Value value : record.values()) {
                    var node = value.asNode();
                    response.add(node.asMap());
                }
            }

        } catch(Neo4jException neo4jException) {
            throw neo4jException;
        }

        return response;
    }
}
