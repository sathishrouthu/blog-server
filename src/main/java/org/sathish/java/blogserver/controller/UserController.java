package org.sathish.java.blogserver.controller;

import jakarta.validation.Valid;
import org.sathish.java.blogserver.dto.LoginRequestDTO;
import org.sathish.java.blogserver.dto.PostInfoDTO;
import org.sathish.java.blogserver.dto.UserCreateDTO;
import org.sathish.java.blogserver.dto.UserDTO;
import org.sathish.java.blogserver.entity.User;
import org.sathish.java.blogserver.service.UserService;
import org.sathish.java.blogserver.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = convertToEntity(userCreateDTO);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = convertToEntity(userDTO);
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostInfoDTO>> getUserPosts(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PostInfoDTO> lastPosts = postService.findByAuthorId(id);
        return ResponseEntity.ok(lastPosts);
    }

    @GetMapping("/{id}/recent-posts")
    public ResponseEntity<List<PostInfoDTO>> getUserRecentViewedPosts(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PostInfoDTO> lastPosts = postService.getLastViewedPostsByUser(id);
        return ResponseEntity.ok(lastPosts);
    }

    @GetMapping("/{id}/liked-posts")
    public ResponseEntity<List<PostInfoDTO>> getUserLikedPosts(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PostInfoDTO> likedPosts = postService.getLikedPostsByUser(id);
        return ResponseEntity.ok(likedPosts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .contact(user.getContact())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private User convertToEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
                .username(userCreateDTO.getUsername())
                .name(userCreateDTO.getName())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword()) // Note: In a real app, password should be hashed
                .contact(userCreateDTO.getContact())
                .bio(userCreateDTO.getBio())
                .build();
    }

    private User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .contact(userDTO.getContact())
                .bio(userDTO.getBio())
                .build();
    }
}