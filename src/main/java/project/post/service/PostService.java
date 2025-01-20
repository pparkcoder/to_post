package project.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Member;
import project.post.domain.Post;
import project.post.domain.PostStatus;
import project.post.dto.PostDto;
import project.post.repository.MemberRepository;
import project.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    //private final PostStatus postStatus;

    /**
     * 게시글 등록
     */
    @Transactional
    public Long savePost(Long memberId, PostDto postDto){
        Member member = memberRepository.findOne(memberId);
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setStatus(postDto.getPostStatus());
        post.setPostDate(postDto.getPostDate());
        post.setMember(member);
        postRepository.savePost(post);
        return post.getId();
    }

    /**
     * 게시글 조회
     */
    public Post findPostById(Long postId){
        return postRepository.findOne(postId);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, PostDto postDto){
        Post findPost = postRepository.findOne(postId);
        findPost.update(postDto.getTitle(), postDto.getContent(), PostStatus.MODIFY, postDto.getPostDate());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, PostDto postDto){
        Post findPost = postRepository.findOne(postId);
        findPost.update(postDto.getTitle(), postDto.getContent(), PostStatus.DELETE, postDto.getPostDate());
    }
}
