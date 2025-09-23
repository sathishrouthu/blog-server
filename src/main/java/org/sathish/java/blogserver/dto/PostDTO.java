package org.sathish.java.blogserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorUsername;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likes;
    private Integer views;
}