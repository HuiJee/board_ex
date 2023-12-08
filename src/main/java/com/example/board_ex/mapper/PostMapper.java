package com.example.board_ex.mapper;

import com.example.board_ex.domain.PostRequest;
import com.example.board_ex.domain.PostResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    // 게시글 저장
    void save(PostRequest params);

    // 게시글 상세정보 조회
    PostResponse findById(Long id);

    // 게시글 수정
    void update(PostRequest params);

    // 게시글 삭제
    void deleteById(Long id);

    // 게시글 리스트 조회
    List<PostResponse> findAll();

    // 게시글 카운트
    int count();
}
