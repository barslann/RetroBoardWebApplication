package com.coderman.retroboard.service;

import com.coderman.retroboard.domain.Comment;
import com.coderman.retroboard.repo.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service // to mark it as a Spring service
@Transactional(readOnly = true)
public class CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackFor = Exception.class) // any code inside that method will be enclosed inside a transaction and if an exception is thrown,
    // JpaTransaction?Manager will roll back the changes it made in the database within that transaction
    public List<Comment> saveAll(List<Comment> comments){
        LOGGER.info("Saving {}",comments);
        return commentRepository.saveAll(comments);
    }

    public List<Comment> getAllCommentsForToday(){
        LocalDate localDate = LocalDate.now();
        return commentRepository.findByCreatedYearAndMonthAndDay(localDate.getYear(),localDate.getMonth().getValue(),localDate.getDayOfMonth());
    }
}
