package tk.tarajki.meme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.in.FeedbackRequest;
import tk.tarajki.meme.dto.out.CommentDto;
import tk.tarajki.meme.security.UserPrincipal;
import tk.tarajki.meme.services.CommentService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity addFeedback(
            @PathVariable long id,
            @RequestBody @Valid FeedbackRequest feedbackRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        commentService.addFeedback(id, feedbackRequest, principal.getUser());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public CommentDto getFeedback(
            @PathVariable long id
    ) {
        return commentService.getCommentById(id);
    }

}
