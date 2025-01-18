package project.post.dto;

import lombok.Getter;
import lombok.Setter;
import project.post.domain.PostStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    String title;
    String content;
    PostStatus postStatus;
    LocalDateTime postData;
}
