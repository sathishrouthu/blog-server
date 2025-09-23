package org.sathish.java.blogserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDTO {
    private Long id;
    private String title;
    private Long authorId;
    private String authorUsername;
    private String category;
    private LocalDateTime createdAt;
    private Integer views;
    private Integer likes;
}