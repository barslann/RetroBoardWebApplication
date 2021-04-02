package com.coderman.retroboard.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "rb_comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @Enumerated
    private CommentType type;

    @CreatedDate
    private Timestamp createdDate;

    private String createdBy;


}
