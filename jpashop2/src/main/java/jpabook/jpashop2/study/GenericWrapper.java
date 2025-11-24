package jpabook.jpashop2.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

public class GenericWrapper {
    /*
        Result<T> : 제네릭 래퍼
        - 왜 쓰나? : API 응답을 줄 때 단순히 List<Member>를 반환하면,
                   나중에 다른 필드를 넣기 어려움.
          ex) 이름 반환 할 때.
             [ {"name" : "김달봉"}, {"name" : "정달봉"} ]
             -> 요구사항이 몇 명인지도 알려줬으면 좋겠다 하여 만약 count를 추가해야 한다면?
                [ {"name" : "김달봉"}, {"name" : "정달봉"}, 2 ]
                이럴 순 없음.
                [ "count" : 2, "data" : [{"name" : "김달봉"}, {"name" : "정달봉"}]]
                이렇게 데이터를 감쌀 수 있게 해야 함.
                이렇게 응답 전체를 감싸는 박스 wrapper가 필요함 그것이
             -> Result<T> : T는 어떤 타입이든 가능하다는 뜻.
                            즉, API응답을 유연하게 감싸기 위한 Wrapper클래스
     */

    @GetMapping("test/result")
    public Result<String> testResult(){
        // "hello" 라는 문자열을 응답으로 보내고 싶다면?
        // 바로 hello를 리턴하지 않고, result라는 박스로 한번 감싸서 응답.
        // 꼭 Result로 할 필요는 없지만 관례적으로 Result
        return new Result<>("hello");
    };

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
