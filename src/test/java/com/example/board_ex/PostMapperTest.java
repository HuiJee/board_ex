package com.example.board_ex;

import com.example.board_ex.domain.PostRequest;
import com.example.board_ex.domain.PostResponse;
import com.example.board_ex.mapper.PostMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PostMapperTest {

    @Autowired
    PostMapper postMapper;
    
    @Test
    void save(){
        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        postMapper.save(params);

        List<PostResponse> posts = postMapper.findAll();
        log.info("전체 게시글 수는 " + posts.size());
    }

    @Test
    void findById(){
        PostResponse post = postMapper.findById(1L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            log.info(postJson);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        // 스프링 부트에 기본으로 내장되어 있는 Jackson 라이브러리 이용!
        // 게시글의 응답 객체를 JSON 문자열로 변환
        // 객체는 디버깅 해보지 않는 이상 확인이 까다롭기에 JSON 문자열로 변경해서 콘솔 출력

        // JSON은 키-밸류 쌍으로 이루어진 데이터 포맷
    }

    @Test
    void update(){
        PostRequest params = new PostRequest();
        params.setId(2L);
        // id를 그대로 고정해야 하므로 (그래서 애초에 그 아이디 전체를 가져오는 쿼리 만들면 됨)
        params.setTitle("2번 게시물 제목 수정");
        params.setContent("2번 게시물 내용 수정");
        params.setWriter("십이공육");
        params.setNoticeYn(true);
        postMapper.update(params);

        PostResponse post = postMapper.findById(2L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            log.info(postJson);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void delete(){
        log.info("삭제 이전의 전체 게시글 수 : " + postMapper.findAll().size());
        postMapper.deleteById(5L);
        log.info("삭제 이후의 전체 게시글 수 : " + postMapper.findAll().size());
    }
}
