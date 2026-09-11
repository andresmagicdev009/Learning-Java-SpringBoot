package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.User;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(Long id);
}
