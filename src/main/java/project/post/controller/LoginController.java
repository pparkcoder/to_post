package project.post.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.post.domain.Member;
import project.post.service.LoginService;

import javax.naming.Binding;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult result, HttpServletResponse response){

        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginEmail(), loginForm.getPassword());

        if(loginMember == null){
            result.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
