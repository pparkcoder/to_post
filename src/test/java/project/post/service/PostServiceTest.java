package project.post.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Address;
import project.post.domain.Member;
import project.post.domain.Post;
import project.post.domain.PostStatus;
import project.post.dto.PostDto;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;

    @Test
    void 게시글등록(){
        // given
        Long memberId = createMember();
        PostDto postDto = new PostDto("title","content", LocalDateTime.now());

        // when
        Long postId = postService.savePost(memberId, postDto);

        // then
        Post findPost = postService.findPostById(postId);
        Assertions.assertEquals(findPost.getId(), postId);
    }

    @Test
    void 게시글삭제(){
        // given
        Long memberId = createMember();
        PostDto postDto = new PostDto("title","content", LocalDateTime.now());

        // when
        Long postId = postService.savePost(memberId, postDto);
        postService.deletePost(postId);

        // then;
        Post findPost = postService.findPostById(postId);
        Assertions.assertEquals(PostStatus.DELETE, findPost.getStatus(), "게시글 삭제 시 상태는 DELETE");
    }

    @Test
    void 게시글수정(){
        // given
        Long memberId = createMember();
        PostDto postDto = new PostDto("title","content", LocalDateTime.now());

        // when
        Long postId = postService.savePost(memberId, postDto);
        postService.updatePost(postId, postDto);

        // then;
        Post findPost = postService.findPostById(postId);
        Assertions.assertEquals(PostStatus.MODIFY, findPost.getStatus(), "게시글 수정 시 상태는 MODIFY");
    }

    private Long createMember() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setAddress(new Address("city","zip","street"));

        return memberService.join(member);
    }
}