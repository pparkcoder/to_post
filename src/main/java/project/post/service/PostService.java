package project.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Post;
import project.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    public Long savePost(Post post){
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

    /**
     * 게시글 삭제
     */

}
