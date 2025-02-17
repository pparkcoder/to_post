package project.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.post.domain.Member;
import project.post.repository.MemberRepository;
import project.post.session.SessionManager;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
//    public String home(){
//        log.info("home controller 진입");
//        return "main";
//    }

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){
        Member loginMember = (Member)sessionManager.getSession(request);

        if(loginMember == null){
            return "main";
        }

        model.addAttribute("member", loginMember);
        return "home";
    }
}
