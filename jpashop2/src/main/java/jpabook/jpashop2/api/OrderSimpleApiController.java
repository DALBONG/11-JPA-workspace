package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
    Order :
      Order -> Member
      Order -> Delivery
      ㄴ> 공통점 : xToOne 관계.
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        // 2) 아직 프록시 인데?
        for (Order order : all) {
            order.getMember().getName(); // 객체 초기화가 되면서 실제 쿼리를 불러옴.
            order.getDelivery().getAddress();// 객체 초기화가 되면서 실제 쿼리를 불러옴.
        }
        // -> 모든 데이터가 나왔음. 필요없는 데이터까지, api는 필요한 데이터만 노출.
        return all;
        /* 1)
         엔티티를 반환하니, 양방향 연관 관계로 무한 로딩에 걸려버림.
          ㄴ 양 방향중, 한 쪽에 @JsonIgnore를 붙여 끊어준다면?
            : org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor뭔 에러가 뜸.
             -> Order의 member로 가보자. 해결법은 bean등록 하고 메인메소드의.. 짓을 해야 함.

          3) 결론적으로 DTO로 변환해서 사용하자.
        */

    };

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                                        .map(o -> new SimpleOrderDto(o))
                                        .collect(Collectors.toList());
        return result;
        //
    };


        @Data
        static class SimpleOrderDto {
            private Long orderId;
            private String name;
            private LocalDateTime orderDate;
            private OrderStatus orderStatus;
            private Address address;

            public SimpleOrderDto(Order o) {
                orderId = o.getId();
                name = o.getMember().getName(); // 객체 초기화.
                orderDate = o.getOrderDate();
                orderStatus = o.getStatus();
                address = o.getDelivery().getAddress(); // 객체 초기화.
            }
        }
        // -> v2 또한, n+1 문제가 발생, 2건을 조회하는데 쿼리가 5방 나감.
        //  id는 가져올 수 있음, Lazy가 발려져 있는 member와 delivery는 당장 가져올 수 X
        //  객체를 초기화 하면서, 쿼리를 가져옴. 주문이 2건이다 보니, 다시 또 가져오면서 5쿼리 나감.


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){

        List<Order> orders = orderRepository.findAllwithMemDel();
        List<SimpleOrderDto> collect = orders.stream()
                                .map(o -> new SimpleOrderDto(o))
                                .collect(Collectors.toList());
        return collect;
    }



}
