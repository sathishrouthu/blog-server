package org.sathish.java.blogserver.service_impl;

import org.sathish.java.blogserver.entity.User;
import org.sathish.java.blogserver.repository.UserRepository;
import org.sathish.java.blogserver.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (!isUsernameAvailable(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!isEmailAvailable(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOpt;
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Check if username is being changed and if it's available
        if (!existingUser.getUsername().equals(userDetails.getUsername()) &&
            !isUsernameAvailable(userDetails.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check if email is being changed and if it's available
        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
            !isEmailAvailable(userDetails.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setBio(userDetails.getBio());
        existingUser.setContact(userDetails.getContact());

        // Only update password if it's provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return registerUser(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return getUserById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return getUserByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return updateUser(user.getId(), user);
    }
}