package project.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Member;
import project.post.domain.Post;
import project.post.domain.PostStatus;
import project.post.dto.PostDto;
import project.post.repository.MemberRepository;
import project.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 보여지는 페이지의 수
    private static final int PAGE_POST_COUNT = 5; // 페이지당 보여지는 게시글 수

    /**
     * 게시글 등록
     */
    @Transactional
    public Long savePost(Long memberId, PostDto postDto){
        Member member = memberRepository.findOne(memberId);
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setStatus(PostStatus.CREATE);
        post.setPostDate(postDto.getPostDate());
        post.setMember(member);
        postRepository.savePost(post);
        return post.getId();
    }

    /**
     * 게시글 조회
     */
    public List<Post> findPost(){
        return postRepository.findAll();
    }

    /**
     * 제목, 내용으로 게시글 조회
     */
    public List<Post> findPostByCondition(String title, String content, int pageNum){
        int realPageNum = pageNum - 1;
        int offset = PAGE_POST_COUNT * realPageNum;
        int limit = offset + PAGE_POST_COUNT - 1;
        return postRepository.findAllByCriteria(title, content, offset, limit);
    }

    public Post findPostById(Long postId){
        return postRepository.findOne(postId);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, PostDto postDto){
        Post findPost = postRepository.findOne(postId);
        findPost.update(postDto.getTitle(), postDto.getContent(), PostStatus.MODIFY, LocalDateTime.now());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId){
        Post findPost = postRepository.findOne(postId);
        findPost.update(findPost.getTitle(), findPost.getContent(), PostStatus.DELETE, LocalDateTime.now());
    }

    /**
     * 전체 페이지 계산
     */
    public Integer findMaxPageNum(int totalSize){
        return (int)Math.ceil(totalSize/PAGE_POST_COUNT);
    }
}
