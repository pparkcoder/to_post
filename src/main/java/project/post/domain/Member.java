package project.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
