package project.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import project.post.domain.Member;
import project.post.repository.MemberRepository;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
//    public String home(){
//        log.info("home controller 진입");
//        return "main";
//    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

        if(memberId == null){
            return "main";
        }

        Member loginMember = memberRepository.findOne(memberId);
        if(loginMember == null){
            return "main";
        }

        model.addAttribute("member", loginMember);
        return "home";
    }
}
