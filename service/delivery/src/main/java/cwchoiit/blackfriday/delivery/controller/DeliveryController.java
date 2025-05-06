package cwchoiit.blackfriday.delivery.controller;

import cwchoiit.blackfriday.delivery.service.DeliveryService;
import cwchoiit.blackfriday.delivery.service.request.CreateMemberAddressRequest;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.delivery.service.response.DeliveryReadResponse;
import cwchoiit.blackfriday.delivery.service.response.MemberAddressReadResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<MemberAddressReadResponse>> createMemberAddress(@RequestBody CreateMemberAddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(deliveryService.createMemberAddress(request)));
    }

    @PostMapping("/process-delivery")
    public ResponseEntity<ApiResponse<DeliveryReadResponse>> processDelivery(@RequestBody ProcessDeliveryRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(deliveryService.processDelivery(request)));
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<ApiResponse<DeliveryReadResponse>> findDelivery(@PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity.ok(ApiResponse.ok(deliveryService.findDelivery(deliveryId)));
    }

    @GetMapping("/addresses/{memberAddressId}")
    public ResponseEntity<ApiResponse<MemberAddressReadResponse>> findMemberAddress(@PathVariable("memberAddressId") Long memberAddressId) {
        return ResponseEntity.ok(ApiResponse.ok(deliveryService.findMemberAddress(memberAddressId)));
    }

    @GetMapping("/addresses/member/{memberId}")
    public ResponseEntity<ApiResponse<List<MemberAddressReadResponse>>> findAllMemberAddressByMemberId(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(ApiResponse.ok(deliveryService.findMemberAddressByMemberId(memberId)));
    }
}
