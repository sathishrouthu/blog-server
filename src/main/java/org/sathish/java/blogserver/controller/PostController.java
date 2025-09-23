package org.sathish.java.blogserver.controller;

import jakarta.validation.Valid;
import org.sathish.java.blogserver.dto.PostInfoDTO;
import org.sathish.java.blogserver.dto.PostDTO;
import org.sathish.java.blogserver.dto.ActionDTO;
import org.sathish.java.blogserver.entity.Post;
import org.sathish.java.blogserver.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostInfoDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostInfoDTO> posts = postService.getAllPostsPaginated(Pageable.ofSize(size).withPage(page));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getFullPost(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostInfoDTO> createPost(@Valid @RequestBody Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setLikes(0);
        post.setViews(0);
        PostInfoDTO createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostInfoDTO> updatePost(@PathVariable Long id, @Valid @RequestBody Post post) {
        try {
            PostInfoDTO updatedPost = postService.updatePost(id, post);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PostInfoDTO>> getPostsByCategory(@PathVariable String category) {
        List<PostInfoDTO> posts = postService.getPostsByCategory(category);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostInfoDTO>> searchPosts(@RequestParam String keyword) {
        List<PostInfoDTO> posts = postService.searchPostsByTitle(keyword);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/view")
    public ResponseEntity<Void> recordView(@RequestBody ActionDTO actionDTO) {
        try {
            postService.recordView(actionDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/like")
    public ResponseEntity<Void> recordLike(@RequestBody ActionDTO likeDTO) {
        try {
            postService.recordLike(likeDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> unlikePost(@RequestBody ActionDTO unlikeDTO) {
        try {
            postService.unlikePost(unlikeDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/check-like")
    public ResponseEntity<Boolean> checkPostLiked(@RequestBody ActionDTO actionDTO) {
        try {
            boolean isLiked = postService.isPostLikedByUser(actionDTO);
            return ResponseEntity.ok(isLiked);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}