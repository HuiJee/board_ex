package com.example.board_ex;

import com.example.board_ex.domain.PostRequest;
import com.example.board_ex.domain.PostResponse;
import com.example.board_ex.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void save(){
        PostRequest params = new PostRequest();
        params.setTitle("2번 서비스");
        params.setContent("2 서비스 내용");
        params.setWriter("서비스");
        params.setNoticeYn(false);
        Long id = postService.savePost(params);
        log.info("생성된 게시글 id : " + id);
        // 쿼리 적은 xml 파일에 useGeneratedKeys를 넣어줘야 함
        // 아니면 null로만 표시됨
    }

    @Test
    void findById(){
        PostResponse post = postService.findPostById(6L);
        show(post);
        assertThat(post.getWriter()).isEqualTo("서비스");  // 검증 절차
    }

    void show(PostResponse post){
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            log.info(postJson);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void update(){
        PostRequest params = new PostRequest();
        params.setId(6L);
        params.setTitle("1 서비스 제목 수정");
        params.setContent("1 서비스 내용 수정");
        params.setWriter("서비스 수정");
        params.setNoticeYn(true);
        Long id = postService.updatePost(params);
        log.info("수정된 id : " + id);

        PostResponse post = postService.findPostById(6L);
        show(post);
    }

    @Test
    void delete(){
        log.info("삭제 전 전체 게시글 수 : " + postService.findAllPost().size());
        postService.deletePost(6L);
        log.info("삭제 후 전체 게시글 수 : " + postService.findAllPost().size());

        List<PostResponse> list = postService.findAllPost();
        list.forEach(this::show);
    }

}
