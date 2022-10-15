package com.example.springneo4j.service;

import com.example.springneo4j.entity.Person;

import java.util.Set;

public interface PersonService {
    Set<Person> findPersonsWithName(String name);
    Set<Person> addFriend(Long person, Long friend);
    Set<Person> findFriendsWith(Long personId);
    Person savePerson(Person person);

}
