package jpabook.jpashop2;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import jpabook.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

// DI(Dependency Injection)
@RequiredArgsConstructor
public class Injection {

    /*
        1. 필드 주입 : @Autowired를 통해 필드에 의존성 주입
                    -> 간단한 장점이 있으나 테스트나 유지보수에 불편.
    @Autowired
    private MemberRepository memberRepository;
        -> 메모리 데이터를 사용하고싶다 했을 떄 수정 못하여 못만듦
     */

    /*
        2. Setter 주입 : 세터를 @Autowired된 세터 메소드를 호출하고, 수정 가능.
                        -> 단, 다른곳에서 변경이 되어버릴 수 있음
    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /*
        3. ** 생성자 주입 : 스프링이 생성자 호출 시점에 의존성 주입.
                      -> final키워드 사용으로 불변성 보장,
                       직접 new로 주입 가능, 가장 권장!!

    private final MemberRepository memberRepository;

    @RequiredArgsConstructor // final 붙은 것만 매개변수 생성자를 만들어즘 /
    public Injection(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    public static void main(String[] args){

    }

}
