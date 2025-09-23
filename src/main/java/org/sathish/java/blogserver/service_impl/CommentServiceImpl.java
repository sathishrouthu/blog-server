package org.sathish.java.blogserver.service_impl;

import org.sathish.java.blogserver.dto.CommentDTO;
import org.sathish.java.blogserver.entity.Comment;
import org.sathish.java.blogserver.repository.CommentRepository;
import org.sathish.java.blogserver.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserServiceImpl userServiceImpl;

    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userServiceImpl) {
        this.commentRepository = commentRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));

        existingComment.setContent(commentDetails.getContent());

        return commentRepository.save(existingComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        commentRepository.delete(comment);
    }

    /**
     * Converts a Comment entity to a CommentDTO
     * @param comment the comment entity
     * @return CommentDTO with necessary information
     */
    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());

        // Only get user from database if userId is not null
        if (comment.getUserId() != null) {
            dto.setUsername(
                    userServiceImpl.getUserById(comment.getUserId())
                            .map(user -> user.getUsername())
                            .orElse("Unknown")
            );
        } else {
            dto.setUsername("Unknown");
        }

        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}