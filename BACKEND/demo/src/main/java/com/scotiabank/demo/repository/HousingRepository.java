package com.scotiabank.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scotiabank.demo.model.Housing;

import java.util.Optional;

@Repository
public interface HousingRepository extends MongoRepository<Housing, String> {
    Optional<Housing> findByUserId(String userId);
}