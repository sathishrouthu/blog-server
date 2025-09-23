package org.sathish.java.blogserver.repository;

import org.sathish.java.blogserver.entity.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {

    @Modifying
    @Query(value = "INSERT INTO view_history (user_id, post_id, clicked_at) VALUES (:userId, :postId, NOW()) " +
            "ON DUPLICATE KEY UPDATE clicked_at = NOW()", nativeQuery = true)
    void insertOrUpdateViewHistory(@Param("userId") Long userId, @Param("postId") Long postId);

    /**
     * Find the last 10 visited posts by a specific user
     * @param userId the ID of the user
     * @return a list of view history entries for the last 10 posts viewed by the user
     */
    @Query(value = "SELECT * FROM view_history vh WHERE vh.user_id = :userId ORDER BY vh.clicked_at DESC", nativeQuery = true)
    List<ViewHistory> findLastViewedPostsByUser(@Param("userId") Long userId);

    /**
     * Find all view history entries for a specific user
     * @param userId the ID of the user
     * @return a list of all view history entries for the user
     */
    List<ViewHistory> findByUserIdOrderByClickedAtDesc(Long userId);

    /**
     * Find all view history entries for a specific post
     * @param postId the ID of the post
     * @return a list of all view history entries for the post
     */
    List<ViewHistory> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}