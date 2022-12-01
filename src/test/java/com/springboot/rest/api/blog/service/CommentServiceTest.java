package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.exception.NotFoundException;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.CommentRepository;
import com.springboot.rest.api.blog.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.springboot.rest.api.blog.utils.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest()
public class CommentServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    private Post existingPost;
    private Post postMocked = new Post(null, TITLE, CONTENT, now);

    private Comment commentMocked = new Comment(null, CONTENT, AUTHOR, postMocked, now);

    @BeforeEach
    public void setup() {
        existingPost = postRepository.save(postMocked);
    }

    @AfterEach
    public void tearDown() {
        commentRepository
            .findByPostId(existingPost.getId())
            .forEach(comment -> commentRepository.delete(comment));

        postRepository
            .findById(existingPost.getId())
                .ifPresent(p -> postRepository.delete(p));
    }

    @Test
    public void shouldReturnExceptionForNotExistingPostSuccessfully() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            commentMocked.getPost().setId(BigDecimal.ZERO.subtract(BigDecimal.ONE).longValue());
            commentService.addComment(commentMocked);
        });

        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }

    @Test
    public void shouldAddCommentSuccessfully() {
        NEW_COMMENT_MOCKED.setPostId(existingPost.getId());
        Long commentId = commentService.addComment(commentMocked);

        assertThat("Comment id shouldn't be null", commentId, notNullValue());
    }

    @Test
    public void shouldReturnAddedCommentSuccessfully() {
        NEW_COMMENT_MOCKED.setPostId(existingPost.getId());
        commentService.addComment(commentMocked);

        List<Comment> comments = commentService.getCommentsForPost(existingPost.getId());

        assertThat("There should be one comment", comments, hasSize(1));
        assertThat(comments.get(0).getAuthor(), equalTo(COMMENT_MOCKED.getAuthor()));
        assertThat(comments.get(0).getContent(), equalTo(COMMENT_MOCKED.getContent()));
        assertThat(comments.get(0).getCreationDate(), equalTo(COMMENT_MOCKED.getCreationDate()));

    }

    @Test
    public void shouldReturnEmptyArrayCommentsSuccessfully() {
        List<Comment> comments = commentService.getCommentsForPost(existingPost.getId());
        assertThat("There should not have comments related to the postId", comments, hasSize(0));
    }

}
