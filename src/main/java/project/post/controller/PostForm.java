package project.post.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import project.post.domain.Member;

@Getter
@Setter
public class PostForm {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;
    private String content;
}
