package project.post.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @NotEmpty
    private String loginEmail;

    @NotEmpty
    private String password;
}
