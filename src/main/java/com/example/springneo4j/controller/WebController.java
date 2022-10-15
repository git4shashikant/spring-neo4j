package com.example.springneo4j.controller;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;
import com.example.springneo4j.service.AddressService;
import com.example.springneo4j.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/neo4j")
public class WebController {

    private final PersonService personService;
    private final AddressService addressService;

    @Autowired
    public WebController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @GetMapping("/status")
    public String status() {
        return "running";
    }

    @PostMapping(
            value = "/person/save",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Person savePerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @GetMapping("/person/{name}")
    public Set<Person> findPersonWithName(@PathVariable(value="name") String name) {
        return personService.findPersonsWithName(name);
    }

    @PostMapping(
            value = "/person/add-friend/{personId}/{friendId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Set<Person> saveFriend(@PathVariable(value="personId") String personId, @PathVariable(value="friendId") String friendId) {
        return personService.addFriend(Long.parseLong(personId), Long.parseLong(friendId));
    }

    @PostMapping(
            value = "/address/save",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Address saveAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @PostMapping(
            value = "/address/add-resident/{addressId}/{personId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Address addResident(@PathVariable(value="addressId") String addressId, @PathVariable(value="personId") String personId) {
        return addressService.addResident(addressId, personId);
    }

}
