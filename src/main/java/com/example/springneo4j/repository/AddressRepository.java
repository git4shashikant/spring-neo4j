package com.example.springneo4j.repository;

import com.example.springneo4j.entity.Address;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends Neo4jRepository<Address, Long> {
}
