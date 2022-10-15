package com.example.springneo4j.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.HashSet;

@Node
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@Getter
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private int houseNumber;
    private String building;
    private String street;
    private String locality;
    private String countyOrState;
    private String country;
    private int pinCode;

    @Relationship("RESIDENTS")
    private Set<Person> residents;

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCountyOrState(String countyOrState) {
        this.countyOrState = countyOrState;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public void addResident(Person person) {
        if (residents == null) {
            residents = new HashSet<>();
        }

        residents.add(person);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Address{" +
                "houseNumber=" + houseNumber +
                ", building='" + building + '\'' +
                ", street='" + street + '\'' +
                ", locality='" + locality + '\'' +
                ", countyOrState='" + countyOrState + '\'' +
                ", country='" + country + '\'' +
                ", pinCode=" + pinCode +
                ", residents=");

        residents.stream().forEach(resident -> {sb.append(resident.getName());});
        sb.deleteCharAt(sb.lastIndexOf(",")).append('}');

        return sb.toString();
    }
}
