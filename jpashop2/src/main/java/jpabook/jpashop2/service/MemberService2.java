package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService2 {

    private final MemberRepository2 memberRepository2;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        return memberRepository2.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository2.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원데스");
        }
    }

    public List<Member> findMembers () {
        return memberRepository2.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository2.findOne(memberId);
    }

}
