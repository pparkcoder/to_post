package project.post.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.post.domain.PostStatus;
import project.post.dto.PostDto;
import project.post.domain.Post;
import project.post.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 조회
     */
    @GetMapping("/api/posts")
    public Result posts(){
        List<Post> findPosts = postService.findPost();
        List<PostDto2> collect = findPosts.stream()
                .map(p -> new PostDto2(p.getId(), p.getTitle()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/posts/{id}")
    public UpdatePostResponse updatePost(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdatePostRequest request){
        PostDto postDto = new PostDto(request.getTitle(), request.getContent(), LocalDateTime.now());
        postService.updatePost(id, postDto);
        Post findPost = postService.findPostById(id);
        return new UpdatePostResponse(findPost.getId(), findPost.getTitle(), findPost.getContent(), findPost.getStatus());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PostDto2{
        Long id;
        String title;
    }

    @Data
    static class UpdatePostRequest{
        @NotEmpty
        private String title;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePostResponse{
        private Long id;
        private String title;
        private String content;
        private PostStatus status;
    }

    @Data
    static class DeletePostRequest{
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class DeletePostResponse{
        private Long id;
        private PostStatus status;
    }
}
