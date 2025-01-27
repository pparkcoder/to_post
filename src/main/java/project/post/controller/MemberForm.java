package project.post.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;
    private String city;
    private String zip;
    private String street;
}
