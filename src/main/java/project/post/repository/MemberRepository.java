package project.post.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.post.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public List<Member> findByEmail(String memberEmail){
        return em.createQuery("select m from Member m"
                +" where m.email = :email", Member.class)
                .setParameter("email",memberEmail)
                .getResultList();
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findOne(Long memberId){
        return em.find(Member.class, memberId);
    }

}
