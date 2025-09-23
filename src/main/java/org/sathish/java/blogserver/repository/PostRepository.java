package org.sathish.java.blogserver.repository;

import org.sathish.java.blogserver.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts by a specific author ID
     * @param authorId the ID of the author
     * @return a list of posts by the author
     */
    List<Post> findByAuthorId(Long authorId);

    /**
     * Find all posts by category
     * @param category the category to filter by
     * @return a list of posts in the specified category
     */
    List<Post> findByCategory(String category);

    /**
     * Find all posts containing a keyword in the title
     * @param keyword the keyword to search for
     * @return a list of posts with titles containing the keyword
     */
    List<Post> findByTitleContaining(String keyword);
}