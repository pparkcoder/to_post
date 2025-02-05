package project.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Member;
import project.post.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 중복 조회
     */

    private void validMember(Member member){
        int result = memberRepository.findByEmail(member.getEmail());
        if(result > 0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public void update(Long id, String email){
        Member member = memberRepository.findOne(id);
        member.setEmail(email);
    }
}
