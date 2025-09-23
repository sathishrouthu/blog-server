package org.sathish.java.blogserver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
