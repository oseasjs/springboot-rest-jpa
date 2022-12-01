package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.service.CommentService;
import com.springboot.rest.api.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected PostService postService;

    @MockBean
    protected CommentService commentService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(postService, commentService);
    }

}
