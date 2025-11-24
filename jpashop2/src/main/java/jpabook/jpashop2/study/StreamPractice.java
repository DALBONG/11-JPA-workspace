package jpabook.jpashop2.study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamPractice {
    public static void main(String[] args) {

        // 1. 기본 ArrayList 만들기
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println("원본리스트 : " + list);

        // 2. 각 숫자에 2를 곱해서 새로운 리스트 반환하고 싶다면?
        List<Integer> list2 = new ArrayList<>();
        for (Integer num : list){
            list2.add(num * 2);
        }
        System.out.println("*2 결과 : "+ list2 );

        /*
            3. Java8의 stream() + map() 사용.
               - map() : 리스트의 요소를 하나씩 순회하여 반환.
               - collect() : 다시 리스트로 모아, 최종적으로 for문과 똑같은 결과
        */
        List<Integer> list3 = list.stream().map(n -> n * 2).collect(Collectors.toList());
        // n : map()에서 제공하는 객체로, list의 결과가 담겨있음.
        // Collectors.toList : 콜렉터 타입을 리스트 형으로 모아줘.
        System.out.println("stream 결과 : " + list3);

    }

}
