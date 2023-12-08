package com.example.board_ex.controller;

import com.example.board_ex.domain.PostResponse;
import com.example.board_ex.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class PostController {

    private final PostService postService;

    @GetMapping("write")
    public String openPostWrite(@RequestParam(value="id", required = false) final Long id, Model model){
        if(id != null){
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }
        return "/post/write";
    }


}
