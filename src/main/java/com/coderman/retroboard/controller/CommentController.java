package com.coderman.retroboard.controller;

import com.coderman.retroboard.domain.Comment;
import com.coderman.retroboard.domain.CommentType;
import com.coderman.retroboard.service.CommentService;
import org.apache.maven.surefire.shared.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /*
        This method will load comments made on the day using CommentService.getAllCommentsForToday().
        After that, it will group all comments by comment type and send those to be displayed on the comment page
     */
    @GetMapping ("/")
    public String getComments(Model model){
        model.addAttribute("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType,List<Comment>> groupedComments = allComments.stream().collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));

        return "comment";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }






    /*
        This method mapped to the URL /comment.
        It will save comments made on the day using commentService.saveAll(comments) method
     */

    @PostMapping("/comment")
    public String createComment(@RequestParam(name = "plusComment",required = false) String plusComment,
                                @RequestParam(name = "deltaComment",required = false) String deltaComment,
                                @RequestParam(name = "starComment",required = false) String starComment
                                ) {
        List<Comment> comments = new ArrayList<>();

        if (StringUtils.isNotEmpty(plusComment)){
            comments.add(getComment(plusComment,CommentType.PLUS));
        }

        if (StringUtils.isNotEmpty(starComment)){
            comments.add(getComment(plusComment,CommentType.STAR));
        }

        if (StringUtils.isNotEmpty(deltaComment)){
            comments.add(getComment(plusComment,CommentType.DELTA));
        }


        if(!comments.isEmpty()){
            LOGGER.info("Saved {}", commentService.saveAll(comments));
        }

        return "redirect:/";
    }

    private Comment getComment(String comment, CommentType commentType) {
        Comment commentObject = new Comment();
        commentObject.setType(commentType);
        commentObject.setComment(comment);

        return commentObject;
    }


}
