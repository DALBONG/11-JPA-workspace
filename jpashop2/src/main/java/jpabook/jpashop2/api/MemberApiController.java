//package jpabook.jpashop2.api;
//
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotEmpty;
//import jpabook.jpashop2.domain.Member;
//import jpabook.jpashop2.service.MemberService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController //@Controller + @ResponseBody +...
//@RequiredArgsConstructor
//public class MemberApiController {
//
//    private final MemberService memberService;
//
//    // 2번째 v1
//    @GetMapping("api/v1/members")
//    public List<Member> membersV1() {
//        return memberService.findMembers();
//        // -> 문제 : 조회할 때, Entity를 반환하면 안됨. (orders까지 조회됨)
//        //   해서 Member orders에 @JsonIgnore를 붙인다면, 조회는 안되나 문제가 생김.
//    }
//
//    @GetMapping("api/v2/members")
//    public Result memberV2() {
//        List<Member> members = memberService.findMembers();
//        // 엔티티를 그대로 반환. 이를 사용자에게 그대로 보여주면 안됨.
//        // 해서 iter를 돌리나, map을 사용!
//        List<MemberDto> collect = members.stream()
//                .map(m -> new MemberDto(m.getName()))
//                .collect(Collectors.toList());
//        // + 몇명인지? 까지 요구사항이 추가되었다면?
//
//        return new Result(collect.size(), collect);
//    }
//
//    ;
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        // + 몇명인지? 까지 요구사항이 추가되었다면?
//        private int count;
//
//        private T data;
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class MemberDto {
//        private String name;
//    }
//
//
//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
//        // 회원가입 : 이름, 나이 등.. 정보를 받아야 함 (member). ↑
//        // @RequestBody : 날린 데이터를 멤버에 매핑해주는 어노테이션.
//        // @Valid(검증) : Member 이름에 @NotEmpty 붙여주면, 데이터를 줄 때 이름값을 주지 않으면 에러가 뜸.
//        // 회원가입이 잘 되었다는 데이터를 알려줘야 함. CreateMemberResponse : DTO.
//        //      외부에서 쓸 필요 없어 이 안에 메소드 만들어줌! (@Data)
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id); // @RestCon...붙이는 순간 JSON으로 값을 넘김. 그냥 그렇게 설계되어있음.
//
//        // 화면에서 처리할 것을 해야하는데, Member에 @NotEmpty를 붙이고,
//        // 엔티티까지 영향을 끼치는 것은 위험함.
//        // 이렇게 하면 실제 회원 삭제시에도 이름을 입력해야 하는 문제점이 발생.
//        // -> 해결 : 엔티티 대신, DTO를 @RequestBody 매핑.
//    }
//
//    ;
//
//    @PostMapping("api/v2/members")
//    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
//
//        Member member = new Member();
//        member.setName(request.getName());
//
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//    }
//
//    ;
//
//    @PutMapping("/api/v2/members/{id}")
//    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
//                                               @RequestBody @Valid
//                                               UpdateMemberRequest request) {
//        // {id}키로 넘겨주려면, @PathVariable 필요.
//        memberService.update(id, request.getName());
//        Member findMember = memberService.findOne(id);
//        // ㄴ 한번 더 조회 하는 것 없이, Service에서 Member를 반환해도 되나,
//        // 그러면 영속성 컨텍스트가 끊김으로, 이어나가기 위해서 이렇게 하는 방식을 선호.
//        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
//    }
//
//    ;
//
//
//    @Data
//    static class CreateMemberRequest {
//        @NotEmpty
//        private String name;
//
//    }
//
//    @Data
//    static class UpdateMemberRequest {
//        @NotEmpty
//        private String name;
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class UpdateMemberResponse {
//        private Long id;
//        private String name;
//    }
//
//
//    @Data
//    static class CreateMemberResponse {
//        private Long id;
//
//        public CreateMemberResponse(Long id) {
//            this.id = id;
//        }
//
//    }
//
//}
