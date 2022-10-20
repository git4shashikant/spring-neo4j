package com.example.springneo4j.service.impl;

import com.example.springneo4j.entity.Person;
import com.example.springneo4j.repository.PersonRepository;
import com.example.springneo4j.service.PersonService;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {

    private final Driver driver;
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(Driver driver, PersonRepository personRepository) {
        this.driver = driver;
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
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

    @Override
    public Set<Person> findPersonFriends(Long personId) {
        Set<Person> friendship = new HashSet<>(2);
        Optional<Person> personEntityOptional = personRepository.findById(personId);
        if (personEntityOptional.isPresent()) {
            friendship.addAll(personEntityOptional.get().getFriends());
        }

        return friendship;
    }

    @Override
    public Map<String,Object> savePersonUsingDriver(Person person) {
        var sessionConfig = SessionConfig.builder()
                .withDefaultAccessMode(AccessMode.WRITE)
                .withDatabase("neo4j")
                .build();

        try (var session = driver.session(sessionConfig)) {
            var res = session.writeTransaction(tx ->
                    tx.run("CREATE (p:Person {name: $name}) RETURN p",
                            Values.parameters("name", person.getName())).single());

            var node =  res.get("p").asNode();
            return node.asMap();
        } catch(Neo4jException neo4jException) {
            throw neo4jException;
        }
    }


}
