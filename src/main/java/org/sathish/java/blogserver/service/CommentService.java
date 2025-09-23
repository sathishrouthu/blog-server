package org.sathish.java.blogserver.service;

import org.sathish.java.blogserver.dto.CommentDTO;
import org.sathish.java.blogserver.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    /**
     * Create a new comment
     * @param comment the comment to create
     * @return the created comment with ID
     */
    Comment createComment(Comment comment);

    /**
     * Get a comment by ID
     * @param id the comment ID
     * @return Optional containing the comment if found
     */
    Optional<Comment> getCommentById(Long id);

    /**
     * Get all comments for a specific post
     * @param postId the ID of the post
     * @return a list of comments for the post
     */
    List<CommentDTO> getCommentsByPostId(Long postId);


    /**
     * Update a comment
     * @param id the comment ID
     * @param commentDetails the updated comment details
     * @return the updated comment
     * @throws IllegalArgumentException if comment not found
     */
    Comment updateComment(Long id, Comment commentDetails);

    /**
     * Delete a comment
     * @param id the comment ID
     * @throws IllegalArgumentException if comment not found
     */
    void deleteComment(Long id);

}