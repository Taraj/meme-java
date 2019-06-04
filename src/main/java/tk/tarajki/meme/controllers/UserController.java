package tk.tarajki.meme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.in.FeedbackRequest;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.dto.out.UserDto;
import tk.tarajki.meme.security.UserPrincipal;
import tk.tarajki.meme.services.UserService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{nickname}")
    public UserDto getUserByNickname(@PathVariable String nickname) {
        return userService.getUserDtoByNickname(nickname);
    }


    @GetMapping("/{nickname}/posts")
    public List<PostDto> getPosts(
            @PathVariable String nickname,
            @RequestParam(name = "offset", defaultValue = "0") long offset,
            @RequestParam(name = "count", defaultValue = "10") long count
    ) {
        return userService.getUserPostsDtoByNickname(nickname, offset, count);
    }


    @PostMapping("/{nickname}/feedback")
    public ResponseEntity addFeedback(
            @PathVariable String nickname,
            @RequestBody @Valid FeedbackRequest feedbackRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.addFeedback(nickname, feedbackRequest, principal.getUser());
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
