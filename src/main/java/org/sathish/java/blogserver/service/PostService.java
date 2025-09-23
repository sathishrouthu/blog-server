package org.sathish.java.blogserver.service;

import org.sathish.java.blogserver.dto.PostDTO;
import org.sathish.java.blogserver.dto.PostInfoDTO;
import org.sathish.java.blogserver.dto.ActionDTO;
import org.sathish.java.blogserver.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    /**
     * Create a new post
     * @param post the post to create
     * @return the created post with ID
     */
    PostInfoDTO createPost(Post post);

    /**
     * Get a post by ID
     * @param id the post ID
     * @return Optional containing the post if found
     */
    Optional<PostDTO> getPostById(Long id);

    /**
     * Get all posts
     * @return a list of all posts
     */
    List<PostInfoDTO> getAllPosts();

    /**
     * Get all posts by a specific author
     * @param authorId the ID of the author
     * @return a list of posts by the author
     */
    List<PostInfoDTO> getPostsByAuthorId(Long authorId);

    /**
     * Get all posts by category
     * @param category the category to filter by
     * @return a list of posts in the specified category
     */
    List<PostInfoDTO> getPostsByCategory(String category);

    /**
     * Search posts by title keyword
     * @param keyword the keyword to search for
     * @return a list of posts with titles containing the keyword
     */
    List<PostInfoDTO> searchPostsByTitle(String keyword);

    /**
     * Update a post
     * @param id the post ID
     * @param postDetails the updated post details
     * @return the updated post
     * @throws IllegalArgumentException if post not found
     */
    PostInfoDTO updatePost(Long id, Post postDetails);

    /**
     * Delete a post
     * @param id the post ID
     * @throws IllegalArgumentException if post not found
     */
    void deletePost(Long id);

    /**
     * Get the last 10 viewed posts by a specific user
     * @param userId the ID of the user
     * @return a list of the last 10 posts viewed by the user
     */
    List<PostInfoDTO> getLastViewedPostsByUser(Long userId);

    List<PostInfoDTO> getLikedPostsByUser(Long userId);

    /**
     * Find all posts by a specific author ID ( user id )
     * @param authorId
     * @return
     */
    List<PostInfoDTO> findByAuthorId(Long authorId);

    Page<PostInfoDTO> getAllPostsPaginated(Pageable pageable);

    void recordView(ActionDTO actionDTO);
    void recordLike(ActionDTO likeDTO);
    void unlikePost(ActionDTO unlikeDTO);
    boolean isPostLikedByUser(ActionDTO actionDTO);



}