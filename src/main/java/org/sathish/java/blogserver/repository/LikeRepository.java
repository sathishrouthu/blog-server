package org.sathish.java.blogserver.repository;

import org.sathish.java.blogserver.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Query(value = "INSERT INTO likes (user_id, post_id, clicked_at) VALUES (:userId, :postId, NOW()) " +
            "ON DUPLICATE KEY UPDATE clicked_at = NOW()", nativeQuery = true)
    void insertOrUpdateLike(@Param("userId") Long userId, @Param("postId") Long postId);
    List<Like> findByUserId(Long userId);
    void deleteByPostId(Long postId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    Boolean existsByUserIdAndPostId(Long userId, Long postId);
}