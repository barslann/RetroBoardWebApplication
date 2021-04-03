package com.coderman.retroboard.repo;

import com.coderman.retroboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    // Since a list of comments for a specific data needs to be retrieved to be shown in the frontend, a custom method qith a @QUERY annotation is added to this interface!
    // This annotation is responsible for using a database-independent SQl query to filter out data from the database
    @Query("SELECT c FROM Comment c WHERE year(c.createdDate) = ?1 AND month(c.createdDate) = ?2  AND day(c.createdDate) = ?3")
    List<Comment> findByCreatedYearAndMonthAndDay(int year, int month, int day);
}

// with this, there is no need to implement anything. Just writing an interface that extends from the JPA repository interface
// will be sufficient to expose methods to find one, find all, save, delete, and so on.