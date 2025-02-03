package project.post.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.post.domain.Address;
import project.post.domain.Member;
import project.post.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getId(), m.getEmail()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setAddress(new Address(request.getCity(), request.getZip(), request.getStreet()));

        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        Long id;
        String email;
    }

    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String email;
        private String city;
        private String zip;
        private String street;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
