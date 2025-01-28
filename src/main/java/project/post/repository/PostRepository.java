package project.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.post.domain.Post;
import project.post.domain.PostStatus;

import java.util.ArrayList;
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

    public List<Post> findAllByCriteria(String title, String content){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> p = cq.from(Post.class);

        List<Predicate> criteria = new ArrayList<>();

        // 제목 검색
        if(StringUtils.hasText(title)){
            Predicate findTitle = cb.like(p.<String>get("title"),"%" + title + "%");
            criteria.add(findTitle);
        }

        // 내용 검색
        if(StringUtils.hasText(content)){
            Predicate findContent = cb.like(p.<String>get("content"),"%" + content + "%");
            criteria.add(findContent);
        }

        Predicate basicCondition = cb.notEqual(p.get("status"), "DELETE");
        criteria.add(basicCondition);

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Post> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
