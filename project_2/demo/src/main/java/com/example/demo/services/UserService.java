package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

/**
 * Business logic layer for {@link User}.
 *
 * Sits between controllers and the {@link UserRepository}: validates input,
 * enforces business rules (e.g. unique email) and delegates persistence.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor injection. Spring resolves the {@link UserRepository} bean
     * (currently {@code InMemoryUserRepository}) automatically.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns every stored user.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Looks up a user by id.
     *
     * @return an empty Optional when no user matches
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Looks up a user by email (case-insensitive, handled by the repository).
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Creates a new user.
     *
     * Rules:
     * - name, email and password are required
     * - email must not already be in use
     *
     * @throws IllegalArgumentException when validation fails
     */
    public User create(User user) {
        validate(user);

        // Ensure the email is unique before persisting
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }

        // Force null id so the repository assigns a fresh one
        user.setId(null);
        return userRepository.save(user);
    }

    /**
     * Updates an existing user identified by {@code id}.
     *
     * Only name, email and password are updated. The email uniqueness check
     * ignores the user being updated so they can keep their current email.
     *
     * @throws IllegalArgumentException when the user does not exist or
     *                                  validation fails
     */
    public User update(Long id, User changes) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        validate(changes);

        // Reject the new email if it belongs to a different user
        userRepository.findByEmail(changes.getEmail())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new IllegalArgumentException("Email already in use: " + changes.getEmail());
                });

        existing.setName(changes.getName());
        existing.setEmail(changes.getEmail());
        existing.setPassword(changes.getPassword());

        return userRepository.save(existing);
    }

    /**
     * Deletes a user by id.
     *
     * @throws IllegalArgumentException when the user does not exist
     */
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Basic field validation shared by create and update.
     */
    private void validate(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (isBlank(user.getName())) {
            throw new IllegalArgumentException("Name is required");
        }
        if (isBlank(user.getEmail())) {
            throw new IllegalArgumentException("Email is required");
        }
        if (isBlank(user.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
