package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST endpoints for {@link User}.
 *
 * Base path: /api/users
 *
 * Delegates all business logic to {@link UserService}; this class only
 * maps HTTP requests/responses.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "CRUD operations for users")
public class UserController {

    private UserService userService;

    /**
     * Constructor injection of the service layer.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users
     * Returns all users.
     */
    @GetMapping
    @Operation(summary = "List all users")
    @ApiResponse(responseCode = "200", description = "List of users")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * GET /api/users/{id}
     * Returns 200 with the user, or 404 if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> findById(
            @Parameter(description = "User id", example = "1") @PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users
     * Creates a user. Returns 201 with Location header pointing to the new resource.
     */
    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Validation error or email already in use")
    })
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.create(user);
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/users/{id}
     * Replaces name, email and password of an existing user.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Validation error or email already in use"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public User update(
            @Parameter(description = "User id", example = "1") @PathVariable Long id,
            @RequestBody User user) {
        return userService.update(id, user);
    }

    /**
     * DELETE /api/users/{id}
     * Returns 204 on success.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "User id", example = "1") @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
