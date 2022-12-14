package com.example.springneo4j.controller;

import com.example.springneo4j.entity.Address;
import com.example.springneo4j.entity.Person;
import com.example.springneo4j.service.AddressService;
import com.example.springneo4j.service.PersonService;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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

    @PostMapping(
            value = "/person/save-using-driver",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Map<String,Object> savePersonUsingDriver(@RequestBody Person person) {
        return personService.savePersonUsingDriver(person);
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

    @GetMapping("/person/get-friends/{personId}")
    public Set<Person> findPersonFriends(@PathVariable(value="personId") String personId) {
        return personService.findPersonFriends(Long.parseLong(personId));
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
        return addressService.addResident(Long.parseLong(addressId), Long.parseLong(personId));
    }

    @GetMapping("/address/get-residents/{addressId}")
    public Set<Person> findAddressResidents(@PathVariable(value="addressId") String addressId) {
        return addressService.findResidents(Long.parseLong(addressId));
    }

    @GetMapping("/address/{country}/get-residents")
    public List<Map<String, Object>> findAddressResidentsFromSameCountry(@PathVariable(value="country") String country) {
        return addressService.findPersonFromSameCountry(country);
    }

}
