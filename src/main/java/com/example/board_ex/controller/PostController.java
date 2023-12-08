package com.example.board_ex.controller;

import com.example.board_ex.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class PostController {

    private final PostService postService;

    @GetMapping("write")
    public String openPostWrite(Model model){
        String title ="제목",
                content = "내용",
                writer = "홍길동";
        model.addAttribute("t", title);
        model.addAttribute("c", content);
        model.addAttribute("w", writer);
        return "/post/write";
    }


}
