package project.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.post.domain.Post;
import project.post.dto.PostDto;
import project.post.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/boards/new")
    public String createFrom(Model model){
        model.addAttribute("postForm", new PostForm());
        return "boards/createPostForm";
    }

    @PostMapping("/boards/new")
    public String create(@Valid PostForm postForm, BindingResult result){
        if(result.hasErrors()){
            return "boards/createPostForm";
        }

        PostDto postDto = new PostDto(postForm.getTitle(), postForm.getContent(), LocalDateTime.now());
        postService.savePost(1L, postDto);
        return "redirect:/";
    }

//    @GetMapping("/boards")
//    public String list(Model model){
//        List<Post> boards = postService.findPost();
//        model.addAttribute("boards", boards);
//        return "boards/postList";
//    }

    @GetMapping("/boards")
    public String listByCondition(@ModelAttribute("postForm") PostForm postForm, Model model){
        List<Post> boards = postService.findPostByCondition(postForm.getTitle(), postForm.getContent());
        model.addAttribute("boards", boards);
        return "boards/postList";
    }

    @PostMapping("/boards/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/boards";
    }

    /**
     * 수정 전 데이터 조회
     */
    @GetMapping("/boards/{postId}/update")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model){
        Post findPost = postService.findPostById(postId);
        PostForm postForm = new PostForm();
        postForm.setId(findPost.getId());
        postForm.setTitle(findPost.getTitle());
        postForm.setContent(findPost.getContent());

        model.addAttribute("postForm", postForm);
        return "boards/updatePostForm";
    }

    @PostMapping("/boards/{postId}/update")
    public String updatePost(@PathVariable("postId") Long postId, @ModelAttribute("postForm") PostForm postForm){
        PostDto postDto= new PostDto(postForm.getTitle(), postForm.getContent(), LocalDateTime.now());
        postService.updatePost(postId, postDto);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{postId}/detail")
    public String detail(@PathVariable("postId") Long postId, Model model){
        Post findPost = postService.findPostById(postId);
        PostForm postForm = new PostForm();
        postForm.setId(postId);
        postForm.setTitle(findPost.getTitle());
        postForm.setContent(findPost.getContent());

        model.addAttribute("postForm", postForm);
        return "boards/detailPostForm";
    }
}
