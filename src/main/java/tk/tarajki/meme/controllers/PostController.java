package tk.tarajki.meme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.in.CommentRequest;
import tk.tarajki.meme.dto.in.FeedbackRequest;
import tk.tarajki.meme.dto.in.PostRequest;
import tk.tarajki.meme.dto.out.CommentDto;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.security.UserPrincipal;
import tk.tarajki.meme.services.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<PostDto> getAllPosts(
            @RequestParam(value = "offset", defaultValue = "0") long offset,
            @RequestParam(value = "count", defaultValue = "10") long count,
            @RequestParam(value = "confirmed", defaultValue = "true") boolean confirmed
    ) {
        return postService.getAllPostDto(offset, count, confirmed);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(
            @PathVariable long id
    ) {
        return postService.getPostDtoByPostId(id);
    }

    @GetMapping("/random")
    public PostDto getRandomPost() {
        return postService.getRandomPostDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(
            @PathVariable long id
    ) {
        postService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity acceptPostById(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable long id
    ) {
        postService.accept(id, principal.getUser());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getPostComments(
            @PathVariable long id
    ) {
        return postService.getAllCommentDtoByPostId(id);
    }

    @PostMapping("")
    public ResponseEntity addNewPost(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid PostRequest postRequest
    ) {
        postService.addPost(postRequest, principal.getUser());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity addNewComment(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable long id,
            @RequestBody @Valid CommentRequest commentRequest
    ) {
        postService.addComment(id, commentRequest, principal.getUser());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity addFeedback(
            @PathVariable long id,
            @RequestBody @Valid FeedbackRequest feedbackRequest,
            HttpServletRequest request
    ) {
        postService.addFeedback(id, feedbackRequest, request.getRemoteAddr());
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
