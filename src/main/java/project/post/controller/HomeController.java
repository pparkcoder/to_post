package project.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.post.domain.Member;
import project.post.repository.MemberRepository;
import project.post.session.SessionConst;
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
        HttpSession session = request.getSession(false);

        if(session == null){
            return "main";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home
        if(loginMember == null){
            return "main";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home";
    }
}
