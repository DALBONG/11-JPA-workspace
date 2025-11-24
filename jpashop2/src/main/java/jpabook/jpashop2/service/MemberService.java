package jpabook.jpashop2.service;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증(이름으로)
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원데스");
        }
    }

    /**
     * 회원 전체 조회
     * @return
     */
    //@Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, @NotEmpty String name) {
        Member member = memberRepository.findOne(id);
        // fineOne을 통해 영속성 컨텍스트에 있는 것을 조회 함.
        // 여기서 반환을 Member로 할 경우, DirthCheking(변경감지)가 일어나면서
        // 커밋을 하기 때문에, 영속성 컨텍스트가 끊김.
        member.setName(name);
    }
}
