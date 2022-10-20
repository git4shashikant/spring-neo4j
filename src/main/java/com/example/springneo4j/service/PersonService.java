package com.example.springneo4j.service;

import com.example.springneo4j.entity.Person;

import java.util.Map;
import java.util.Set;

public interface PersonService {
    Person savePerson(Person person);
    Map<String,Object> savePersonUsingDriver(Person person);
    Set<Person> findPersonsWithName(String name);
    Set<Person> addFriend(Long person, Long friend);
    Set<Person> findPersonFriends(Long personId);
}
