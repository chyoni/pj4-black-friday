package cwchoiit.blackfriday.order.controller;

import cwchoiit.blackfriday.order.service.ProductOrderService;
import cwchoiit.blackfriday.order.service.request.FinishOrderRequest;
import cwchoiit.blackfriday.order.service.request.StartOrderRequest;
import cwchoiit.blackfriday.order.service.response.ProductOrderDetailReadResponse;
import cwchoiit.blackfriday.order.service.response.ProductOrderReadResponse;
import cwchoiit.blackfriday.order.service.response.StartOrderResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping("/start-order")
    public ResponseEntity<ApiResponse<StartOrderResponse>> startOrder(@RequestBody StartOrderRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(productOrderService.startOrder(request)));
    }

    @PostMapping("/finish-order")
    public ResponseEntity<ApiResponse<ProductOrderDetailReadResponse>> finishOrder(@RequestBody FinishOrderRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(productOrderService.finishOrder(request)));
    }

    @GetMapping("/members/{memberId}/orders")
    public ResponseEntity<ApiResponse<List<ProductOrderReadResponse>>> findOrdersByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(ApiResponse.ok(productOrderService.findOrdersByMemberId(memberId)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<ProductOrderDetailReadResponse>> findOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(ApiResponse.ok(productOrderService.findOrderDetail(orderId)));
    }
}
