package com.example.springneo4j.repository;

import com.example.springneo4j.entity.Address;
import org.neo4j.driver.Result;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends Neo4jRepository<Address, Long> {
    @Query("MATCH (p:Person) -[livesAt:LIVES_AT] ->(a:Address) where a.country = $country return p")
    Result findPersonFromSameCountry(@Param("country") String country);
}
