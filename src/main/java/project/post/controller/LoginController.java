package project.post.controller;

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
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult result){

        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginEmail(), loginForm.getPassword());

        if(loginMember == null){
            result.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        return "home";
    }
}
