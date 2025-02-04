package project.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PostStatus status;
    private LocalDateTime postDate;

    public void update(String title, String content, PostStatus status, LocalDateTime postDate){
        this.title = title;
        this.content = content;
        this.status = status;
        this.postDate = postDate;
    }
}
