package com.example.springneo4j.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Person")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@Getter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Property("address")
    @Relationship(type = "LIVES_AT")
    private Address address;

    @Property("friends")
    @Relationship(type = "FRIEND_WITH")
    private Set<Person> friends;

    public void setName(String name) {
        this.name = name;
    }

    public void livesAt(Address newAddress) {
        address = newAddress;
    }

    public void friendWith(Person person) {
        if (friends == null) {
            friends = new HashSet<>();
        }

        friends.add(person);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Person{" +
                "name='" + name + '\'');
        if (address != null) {
            sb.append(", address=" + address.getCountry() );
        }

        if (friends != null && !friends.isEmpty()) {
            sb.append(", friends=");
            friends.forEach(friend -> sb.append(friend.getName()).append(","));
            sb.deleteCharAt(sb.lastIndexOf(","));
        }

        sb.append('}');

        return sb.toString();
    }
}
