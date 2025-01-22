package project.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.post.domain.Address;
import project.post.domain.Member;
import project.post.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberFrom", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping
    public String create(MemberForm memberForm){
        Address address = new Address(memberForm.getCity(), memberForm.getZip(), memberForm.getStreet());
        Member member = new Member();
        member.setEmail(memberForm.getEmail());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
