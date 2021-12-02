package com.leverx.project.dto;

import com.leverx.project.model.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Integer id;
    private String text;
    private LocalDateTime created_at;
    private Integer author_id;

    public Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setCreated_at(commentDTO.getCreated_at());
        return comment;
    }
}
