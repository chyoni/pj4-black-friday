package cwchoiit.blackfriday.delivery.controller;

import cwchoiit.blackfriday.delivery.service.DeliveryService;
import cwchoiit.blackfriday.delivery.service.request.CreateMemberAddressRequest;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.delivery.service.response.DeliveryReadResponse;
import cwchoiit.blackfriday.delivery.service.response.MemberAddressReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/addresses")
    public MemberAddressReadResponse createMemberAddress(@RequestBody CreateMemberAddressRequest request) {
        return deliveryService.createMemberAddress(request);
    }

    @PostMapping("/process-delivery")
    public DeliveryReadResponse processDelivery(@RequestBody ProcessDeliveryRequest request) {
        log.info("[processDelivery] request: {}", request);
        return deliveryService.processDelivery(request);
    }

    @GetMapping("/{deliveryId}")
    public DeliveryReadResponse findDelivery(@PathVariable("deliveryId") Long deliveryId) {
        return deliveryService.findDelivery(deliveryId);
    }

    @GetMapping("/addresses/{memberAddressId}")
    public MemberAddressReadResponse findMemberAddress(@PathVariable("memberAddressId") Long memberAddressId) {
        return deliveryService.findMemberAddress(memberAddressId);
    }

    @GetMapping("/addresses/member/{memberId}")
    public List<MemberAddressReadResponse> findAllMemberAddressByMemberId(@PathVariable("memberId") Long memberId) {
        return deliveryService.findMemberAddressByMemberId(memberId);
    }
}
