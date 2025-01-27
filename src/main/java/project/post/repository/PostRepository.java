package project.post.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import project.post.domain.Post;
import project.post.domain.PostStatus;

import java.util.List;

@Repository
@AllArgsConstructor
public class PostRepository {

    private EntityManager em;

    public void savePost(Post post){
        em.persist(post);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p where p.status <> :postStatus", Post.class)
                .setParameter("postStatus", PostStatus.DELETE)
                .getResultList();
    }

    public Post findOne(Long postId){
        return em.find(Post.class, postId);
    }
}
