package project.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.post.domain.Member;
import project.post.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * size가 0 이면 로그인 실패
     */
    public Member login(String email, String password){
        List<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.size() == 0 || !password.equals(findMember.get(0).getPassword())){
            return null;
        }

        return findMember.get(0);
    }
}
