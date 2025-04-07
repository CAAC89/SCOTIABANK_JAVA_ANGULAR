package com.scotiabank.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.scotiabank.demo.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}