package cwchoiit.blackfriday.order.controller;

import cwchoiit.blackfriday.order.service.ProductOrderService;
import cwchoiit.blackfriday.order.service.request.FinishOrderRequest;
import cwchoiit.blackfriday.order.service.request.StartOrderRequest;
import cwchoiit.blackfriday.order.service.response.ProductOrderDetailReadResponse;
import cwchoiit.blackfriday.order.service.response.ProductOrderReadResponse;
import cwchoiit.blackfriday.order.service.response.StartOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping("/start-order")
    public StartOrderResponse startOrder(@RequestBody StartOrderRequest request) {
        return productOrderService.startOrder(request);
    }

    @PostMapping("/finish-order")
    public ProductOrderDetailReadResponse finishOrder(@RequestBody FinishOrderRequest request) {
        return productOrderService.finishOrder(request);
    }

    @GetMapping("/members/{memberId}/orders")
    public List<ProductOrderReadResponse> findOrdersByMember(@PathVariable("memberId") Long memberId) {
        return productOrderService.findOrdersByMemberId(memberId);
    }

    @GetMapping("/{orderId}")
    public ProductOrderDetailReadResponse findOrder(@PathVariable("orderId") Long orderId) {
        return productOrderService.findOrderDetail(orderId);
    }
}
