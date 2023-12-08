package com.example.board_ex.controller;

import com.example.board_ex.domain.PostRequest;
import com.example.board_ex.domain.PostResponse;
import com.example.board_ex.dto.MessageDto;
import com.example.board_ex.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

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

    @PostMapping("save")
    public RedirectView savePost(final PostRequest params){
        postService.savePost(params);
        //return "redirect:list";
        return new RedirectView("list");
    }

    @GetMapping("list")
    public String openPostList(Model model){
        List<PostResponse> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "/post/list";
    }

    @GetMapping("view")
    public String openPostView(@RequestParam final Long id, Model model){
        PostResponse post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "/post/view";
    }

    @PostMapping("update")
    public RedirectView updatePost(final PostRequest params){
        postService.updatePost(params);
        return new RedirectView("list");
    }

    @PostMapping("delete")
    public RedirectView deletePost(@RequestParam final Long id){
        postService.deletePost(id);
        return new RedirectView("list");
    }

    private String showMessageAndRedirect(final MessageDto params, Model model){
        model.addAttribute("params", params);
        return "/common/messageRedirect";
    }

}
