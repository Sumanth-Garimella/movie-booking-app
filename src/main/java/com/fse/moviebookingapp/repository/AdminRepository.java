package com.fse.moviebookingapp.repository;

import com.fse.moviebookingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);
}
