package tk.tarajki.meme.services;

import org.springframework.stereotype.Service;
import tk.tarajki.meme.dto.in.FeedbackRequest;
import tk.tarajki.meme.dto.out.CommentDto;
import tk.tarajki.meme.exceptions.ResourceAlreadyExistException;
import tk.tarajki.meme.exceptions.ResourceNotFoundException;
import tk.tarajki.meme.exceptions.UserAuthException;
import tk.tarajki.meme.models.Comment;
import tk.tarajki.meme.models.CommentFeedback;
import tk.tarajki.meme.models.User;
import tk.tarajki.meme.repositories.CommentFeedbackRepository;
import tk.tarajki.meme.repositories.CommentRepository;

import javax.transaction.Transactional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private CommentFeedbackRepository commentFeedbackRepository;

    public CommentService(CommentRepository commentRepository, CommentFeedbackRepository commentFeedbackRepository) {
        this.commentRepository = commentRepository;
        this.commentFeedbackRepository = commentFeedbackRepository;
    }

    @Transactional
    public void addFeedback(long id, FeedbackRequest feedbackRequest, User author) {
        if (author.getActivationToken() != null) {
            throw new UserAuthException("Account inactive.");
        }
        Comment comment = commentRepository.findCommentById(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment not found.");
        }

        if (commentFeedbackRepository.findCommentFeedbackByAuthorAndTarget(author, comment) == null) {
            commentFeedbackRepository.save(
                    new CommentFeedback(
                            feedbackRequest.isLike(),
                            author,
                            comment
                    )
            );
        } else {
            throw new ResourceAlreadyExistException("You already vote.");
        }
    }

   public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            return null;
        }
        return new CommentDto(comment);
    }
}
