package org.sathish.java.blogserver.service;

import org.sathish.java.blogserver.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Register a new user
     * @param user the user to register
     * @return the registered user with ID
     * @throws IllegalArgumentException if username or email already exists
     */
    User registerUser(User user);

    /**
     * Authenticate a user
     * @param username the username
     * @param password the password
     * @return Optional containing the user if authentication succeeds
     */
    Optional<User> authenticateUser(String username, String password);

    /**
     * Get a user by ID
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> getUserById(Long id);

    /**
     * Get a user by username
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Get all users
     * @return list of all users
     */
    List<User> getAllUsers();

    /**
     * Update a user
     * @param id the user ID
     * @param userDetails the updated user details
     * @return the updated user
     * @throws IllegalArgumentException if user not found
     */
    User updateUser(Long id, User userDetails);

    /**
     * Delete a user
     * @param id the user ID
     * @throws IllegalArgumentException if user not found
     */
    void deleteUser(Long id);

    /**
     * Check if a username is available
     * @param username the username to check
     * @return true if username is available, false otherwise
     */
    boolean isUsernameAvailable(String username);

    /**
     * Check if an email is available
     * @param email the email to check
     * @return true if email is available, false otherwise
     */
    boolean isEmailAvailable(String email);

    /**
     * Save a new user (alias for registerUser)
     * @param user the user to save
     * @return the saved user with ID
     */
    User saveUser(User user);

    /**
     * Find a user by ID (alias for getUserById)
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Find a user by username (alias for getUserByUsername)
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Update a user
     * @param user the user with updated details
     * @return the updated user
     * @throws IllegalArgumentException if user not found
     */
    User updateUser(User user);
}