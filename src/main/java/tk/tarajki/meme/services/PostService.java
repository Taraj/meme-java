package tk.tarajki.meme.services;


import org.springframework.stereotype.Service;
import tk.tarajki.meme.dto.in.CommentRequest;
import tk.tarajki.meme.dto.in.FeedbackRequest;
import tk.tarajki.meme.dto.in.PostRequest;
import tk.tarajki.meme.dto.out.CommentDto;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.exceptions.ResourceAlreadyExistException;
import tk.tarajki.meme.exceptions.ResourceNotFoundException;
import tk.tarajki.meme.exceptions.UserAuthException;
import tk.tarajki.meme.models.Comment;
import tk.tarajki.meme.models.Post;
import tk.tarajki.meme.models.PostFeedback;
import tk.tarajki.meme.models.User;
import tk.tarajki.meme.repositories.CommentRepository;
import tk.tarajki.meme.repositories.PostFeedbackRepository;
import tk.tarajki.meme.repositories.PostRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private TagService tagService;

    private PostFeedbackRepository postFeedbackRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, TagService tagService, PostFeedbackRepository postFeedbackRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagService = tagService;
        this.postFeedbackRepository = postFeedbackRepository;
    }

    public List<PostDto> getAllPostDto(long offset, long count, boolean confirmed) {
        Iterable<Post> posts = postRepository.findAll();
        return StreamSupport.stream(posts.spliterator(), false)
                .filter(it -> {
                    if (confirmed) {
                        return it.getConfirmedBy() != null;
                    } else {
                        return it.getConfirmedBy() == null;
                    }
                })
                .skip(offset)
                .limit(count)
                .map(PostDto::new).collect(Collectors.toList());

    }

    public PostDto getPostDtoByPostId(long id) {
        return new PostDto(findPostById(id));
    }

    public PostDto getRandomPostDto() {

        List<Post> posts = new ArrayList<>();
        postRepository.findAll().iterator().forEachRemaining(posts::add);

        if (posts.size() == 0) {
            throw new ResourceNotFoundException("Post not found");
        }

        return new PostDto(posts.get(ThreadLocalRandom.current().nextInt(0, posts.size() - 1)));
    }

    @Transactional
    public void accept(long id, User user) {
        if (user.getActivationToken() != null) {
            throw new UserAuthException("Account inactive.");
        }
        Post post = findPostById(id);
        post.setConfirmedAt(LocalDateTime.now());
        post.setConfirmedBy(user);

        postRepository.save(post);
    }

    @Transactional
    public void addComment(long id, CommentRequest commentRequest, User author) {
        if (author.getActivationToken() != null) {
            throw new UserAuthException("Account inactive.");
        }
        Post post = findPostById(id);
        commentRepository.save(new Comment(
                commentRequest.getContent(),
                author,
                post
        ));
    }

    @Transactional
    public void addPost(PostRequest postRequest, User author) {
        if (author.getActivationToken() != null) {
            throw new UserAuthException("Account inactive.");
        }
        postRepository.save(new Post(
                postRequest.getTitle(),
                postRequest.getUrl(),
                postRequest.getTags().stream().map(it -> tagService.getOrCreateTag(it)).collect(Collectors.toList()),
                author
        ));
    }

    public List<CommentDto> getAllCommentDtoByPostId(long id) {

        List<Comment> comments = findPostById(id).getComments();

        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void addFeedback(long id, FeedbackRequest feedbackRequest, String ip) {
        Post post = findPostById(id);
        if (postFeedbackRepository.findPostFeedbackByAuthorIpAndTarget(ip, post) == null) {
            postFeedbackRepository.save(new PostFeedback(
                    ip,
                    feedbackRequest.isLike(),
                    post
            ));
        } else {
            throw new ResourceAlreadyExistException("You already vote.");
        }
    }

    private Post findPostById(long id) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }
        return post;
    }

    @Transactional
    public void delete(long id){
        postRepository.deleteById(id);
    }
}
