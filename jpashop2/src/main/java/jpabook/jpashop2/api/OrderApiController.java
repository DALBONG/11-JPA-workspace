package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderItem;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import jpabook.jpashop2.repository.order.query.OrderQueryDto;
import jpabook.jpashop2.repository.order.query.OrderQueryRepository;
import jpabook.jpashop2.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor // final을 보고 주입.
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); // LAZY 강제 초기화.
                    // 만약, getMember()까지 즉, 엔티티만 호출해서는 강제 초기화가 되지 않음.
            order.getDelivery().getAddress(); //LAZY 강제 초기화.
            List<OrderItem> orderItems = order.getOrderItems(); // 강제 초기화 X
            orderItems.forEach(o -> o.getItem().getName()); // LAZY 강제 초기화
        }

        return all;
    };

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                            .map(o -> new OrderDto(o))
                            .collect(Collectors.toList());
        return result;
    };

    @Data
    static class OrderDto{

        private Long orderId;
        private String name; // 주문자 이름
        private LocalDateTime orderDate; // 주문 시간
        private OrderStatus orderStatus; // 주문 상태
        private Address address; // 배송 주소
        // private List<OrderItem> orderItems; // 구매 물품
        private List<OrderItemDto> orderItems; // 구매 물품



        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // Lazy 강제 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy 강제 초기화.

//            order.getOrderItems().stream().forEach(o -> o.getItem().getName()); // Lazy  강제 초기화
//            orderItems = order.getOrderItems(); // 이것만 해선 안됨.
            orderItems = order.getOrderItems().stream()
                    //.map(orderItem -> new OrderItemDto(orderItem))
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto{

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName(); // Lazy 강제 초기화
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();

        }
    }


    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllwithItem();
        List<OrderDto> result = orders.stream()
                            .map(o -> new OrderDto(o))
                            .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllwithMemDel(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();

    }

}
