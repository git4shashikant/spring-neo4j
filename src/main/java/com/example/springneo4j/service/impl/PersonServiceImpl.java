package com.example.springneo4j.service.impl;

import com.example.springneo4j.entity.Person;
import com.example.springneo4j.repository.PersonRepository;
import com.example.springneo4j.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Set<Person> findPersonsWithName(String name) {
        Set<Person> people = new HashSet<>();
        List<Person> persons = personRepository.findAll();
        for (Person person :persons) {
            if (person.getName().equalsIgnoreCase(name)) {
                people.add(person);
            }
        }

        return people;
    }

    @Override
    public Set<Person> findFriendsWith(Long personId) {
        Set<Person> friends = new HashSet<>();
        Optional<Person> personEntityOptional = personRepository.findById(personId);
        if (personEntityOptional.isPresent()) {
            friends = personEntityOptional.get().getFriends();
        }

        return friends;
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Set<Person> addFriend(Long personId, Long friendId) {
        Set<Person> friendship = new HashSet<>(2);
        Optional<Person> personEntityOptional = personRepository.findById(personId);
        Optional<Person> friendEntityOptional = personRepository.findById(friendId);

        if (personEntityOptional.isPresent() && friendEntityOptional.isPresent()) {
            Person personEntity = personEntityOptional.get();
            Person friendEntity = friendEntityOptional.get();

            personEntity.friendWith(friendEntity);
            friendEntity.friendWith(personEntity);
            personRepository.save(personEntity);
            personRepository.save(friendEntity);

            friendship = Set.of(personEntity, friendEntity);
        }

        return friendship;
    }
}
