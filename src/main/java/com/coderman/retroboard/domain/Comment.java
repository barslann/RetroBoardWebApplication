package com.coderman.retroboard.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity  //to mark the class as a JPA entity so that it will be eligible to be used in JPA persistence environment!
@Table(name = "rb_comment")  // used to mention the table name to which the class needs to be mapped
@EntityListeners(AuditingEntityListener.class) // is used with AuditingEntityListener to dynamically populate the cratedDate and createdBy properties annotated
// @CreatedBy and @CreatedBy in the comment domain model when persisting the comment entry into the table!
@Data //
public class Comment {
    @Id // identity field of the entity
    @GeneratedValue // auto generated value
    private Long id;

    private String comment;

    @Enumerated(EnumType.STRING)  // it is used to notify JPA that the value of the enum CommentType needs to be persisted as a String type in the database!
    private CommentType type;

    @CreatedDate
    private Timestamp createdDate;

    @CreatedBy
    private String createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
