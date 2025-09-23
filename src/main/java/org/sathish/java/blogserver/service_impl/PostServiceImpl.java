package org.sathish.java.blogserver.service_impl;

import lombok.AllArgsConstructor;
import org.sathish.java.blogserver.dto.PostDTO;
import org.sathish.java.blogserver.dto.PostInfoDTO;
import org.sathish.java.blogserver.dto.ActionDTO;
import org.sathish.java.blogserver.entity.Like;
import org.sathish.java.blogserver.entity.Post;
import org.sathish.java.blogserver.entity.User;
import org.sathish.java.blogserver.entity.ViewHistory;
import org.sathish.java.blogserver.repository.CommentRepository;
import org.sathish.java.blogserver.repository.LikeRepository;
import org.sathish.java.blogserver.repository.PostRepository;
import org.sathish.java.blogserver.repository.ViewHistoryRepository;
import org.sathish.java.blogserver.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final UserServiceImpl userServiceImpl;
    private final LikeRepository likesRepository;

    @Override
    @Transactional
    public PostInfoDTO createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return convertToPostInfoDTO(savedPost);
    }

    @Override
    public Optional<PostDTO> getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(this::convertToPostDTO);
    }

    private PostDTO convertToPostDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorId(post.getAuthorId());
        if(post.getAuthorId()!=null){
            dto.setAuthorUsername(
                    userServiceImpl.getUserById(post.getAuthorId())
                            .map(User::getUsername)
                            .orElse("Anonymous")
            );
        } else {
            dto.setAuthorUsername("Anonymous");
        }
        dto.setCategory(post.getCategory());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setLikes(post.getLikes());
        dto.setViews(post.getViews());
        return dto;
    }

    @Override
    public List<PostInfoDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId).stream()
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> getPostsByCategory(String category) {
        return postRepository.findByCategory(category).stream()
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> searchPostsByTitle(String keyword) {
        return postRepository.findByTitleContaining(keyword).stream()
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostInfoDTO updatePost(Long id, Post postDetails) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        existingPost.setTitle(postDetails.getTitle());
        existingPost.setContent(postDetails.getContent());
        existingPost.setCategory(postDetails.getCategory());
        existingPost.setUpdatedAt(java.time.LocalDateTime.now());

        Post updatedPost = postRepository.save(existingPost);
        return convertToPostInfoDTO(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        // Delete associated comments
        commentRepository.deleteByPostId(id);

        // Delete associated view history records
        viewHistoryRepository.deleteByPostId(id);

        // Delete associated like records
        likesRepository.deleteByPostId(id);

        // Delete the post
        postRepository.delete(post);
    }

    @Override
    public List<PostInfoDTO> getLastViewedPostsByUser(Long userId) {
        return viewHistoryRepository.findLastViewedPostsByUser(userId)
                .stream()
                .map(viewHistory -> postRepository.findById(viewHistory.getPostId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> getLikedPostsByUser(Long userId) {
        return likesRepository.findByUserId(userId)
                .stream()
                .map(like -> postRepository.findById(like.getPostId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> findByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId).stream()
                .map(this::convertToPostInfoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Post entity to a PostInfoDTO
     * @param post the post entity
     * @return PostInfoDTO with summary information
     */
    private PostInfoDTO convertToPostInfoDTO(Post post) {
        PostInfoDTO dto = new PostInfoDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setAuthorId(post.getAuthorId());

        // Set author username based on authorId
        if (post.getAuthorId() != null) {
            dto.setAuthorUsername(
                    userServiceImpl.getUserById(post.getAuthorId())
                            .map(User::getUsername)
                            .orElse("Anonymous")
            );
        } else {
            dto.setAuthorUsername("Anonymous");
        }

        dto.setCategory(post.getCategory());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikes(post.getLikes());
        dto.setViews(post.getViews());
        return dto;
    }

    @Override
    public Page<PostInfoDTO> getAllPostsPaginated(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postsPage = postRepository.findAll(sortedPageable);
        return postsPage.map(this::convertToPostInfoDTO);
    }

    @Override
    @Transactional
    public void recordView(ActionDTO actionDTO) {
        // Also increment the view count on the post
        Post post = postRepository.findById(actionDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + actionDTO.getPostId()));
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        viewHistoryRepository.insertOrUpdateViewHistory(actionDTO.getUserId(), actionDTO.getPostId());
    }

    @Override
    @Transactional
    public void recordLike(ActionDTO likeDTO) {
        // Check if the post exists
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + likeDTO.getPostId()));

        // Check if the like already exists
        boolean likeExists = likesRepository.existsByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());

        // Only increment the like count if this is a new like
        if (!likeExists) {
            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
        }

        // Insert or update the like record
        likesRepository.insertOrUpdateLike(likeDTO.getUserId(), likeDTO.getPostId());
    }

    @Override
    @Transactional
    public void unlikePost(ActionDTO unlikeDTO) {
        Like like = likesRepository.findByUserIdAndPostId(unlikeDTO.getUserId(), unlikeDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Like not found for userId: " + unlikeDTO.getUserId() + " and postId: " + unlikeDTO.getPostId()));

        // Decrement the like count on the post
        Post post = postRepository.findById(unlikeDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + unlikeDTO.getPostId()));
        post.setLikes(post.getLikes() - 1);
        postRepository.save(post);

        likesRepository.delete(like);
    }

    @Override
    public boolean isPostLikedByUser(ActionDTO actionDTO) {
        // Check if like exists
        return likesRepository.existsByUserIdAndPostId(actionDTO.getUserId(), actionDTO.getPostId());
    }
}