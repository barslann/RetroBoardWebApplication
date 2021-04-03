package com.coderman.retroboard.service;

import com.coderman.retroboard.domain.Comment;
import com.coderman.retroboard.domain.CommentType;
import com.coderman.retroboard.repo.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

//During Service, test mocking is done the Mockito library to just mock repository method invocations and verify the correct invocation.

@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @MockBean  //-> testing of its functionality has already done in the JPA repository testing.
    private CommentRepository commentRepository;

    private CommentService commentService;

    @Before
    public void init(){
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> commentList = Arrays.asList(comment);
        LocalDate now = LocalDate.now();

        when(commentRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth())).thenReturn(commentList);

        // When
        List<Comment> actualComments = commentService.getAllCommentsForToday();

        // Then
        verify(commentRepository, times(1)).findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
        assertThat(commentList).isEqualTo(actualComments);

    }

    @Test
    public void saveAll_HappyPath_ShouldSave2Comments() {
        // Given
        Comment comment = new Comment();
        comment.setComment("Test Plus");
        comment.setType(CommentType.PLUS);
        comment.setCreatedBy("Bekir");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("Test Star");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("Bekir");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentRepository.saveAll(comments)).thenReturn(comments);

        // When
        List<Comment> saved = commentService.saveAll(comments);

        // Then
        assertThat(saved).isNotEmpty();
        verify(commentRepository, times(1)).saveAll(comments);

    }
}
