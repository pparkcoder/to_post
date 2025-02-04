package project.post.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.post.domain.Address;
import project.post.domain.Member;
import project.post.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 조회
     */
    @GetMapping("/api/members")
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getId(), m.getEmail()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 회원 등록
     */
    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setAddress(new Address(request.getCity(), request.getZip(), request.getStreet()));

        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getEmail());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getEmail());

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
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }

    @Data
    static class UpdateMemberRequest{
        @NotEmpty
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String email;
    }
}
