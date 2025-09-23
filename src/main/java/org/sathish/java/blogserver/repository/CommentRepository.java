package org.sathish.java.blogserver.repository;

import org.sathish.java.blogserver.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Find all comments by a specific post ID
     * @param postId the ID of the post
     * @return a list of comments for the specified post
     */
    List<Comment> findByPostId(Long postId);

    /**
     * Find all comments by a specific user ID
     * @param userId the ID of the user
     * @return a list of comments made by the specified user
     */
    List<Comment> findByUserId(Long userId);

    void deleteByPostId(Long postId);
}