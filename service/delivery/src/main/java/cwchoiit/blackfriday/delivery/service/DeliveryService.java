package cwchoiit.blackfriday.delivery.service;

import cwchoiit.blackfriday.delivery.adapter.DeliveryProcessor;
import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.DeliveryVendor;
import cwchoiit.blackfriday.delivery.entity.MemberAddress;
import cwchoiit.blackfriday.delivery.repository.DeliverRepository;
import cwchoiit.blackfriday.delivery.repository.MemberAddressRepository;
import cwchoiit.blackfriday.delivery.service.request.CreateMemberAddressRequest;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.delivery.service.response.DeliveryReadResponse;
import cwchoiit.blackfriday.delivery.service.response.MemberAddressReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final MemberAddressRepository memberAddressRepository;
    private final DeliverRepository deliverRepository;
    private final List<DeliveryProcessor> deliveryProcessor;

    @Transactional
    public MemberAddressReadResponse createMemberAddress(CreateMemberAddressRequest request) {
        return MemberAddressReadResponse.of(memberAddressRepository.save(
                        MemberAddress.of(
                                request.memberId(),
                                request.address(),
                                request.alias()
                        )
                )
        );
    }

    @Transactional
    public DeliveryReadResponse processDelivery(ProcessDeliveryRequest request) {
        DeliveryProcessor processor = findDeliveryProcessor(request);
        Long referenceCode = processor.process(request);

        return DeliveryReadResponse.of(deliverRepository.save(
                        Delivery.create(
                                request.orderId(),
                                request.productName(),
                                request.productCount(),
                                request.address(),
                                request.vendor(),
                                referenceCode
                        )
                )
        );
    }

    public DeliveryReadResponse findDelivery(Long deliveryId) {
        return deliverRepository.findById(deliveryId)
                .map(DeliveryReadResponse::of)
                .orElseThrow();
    }

    public MemberAddressReadResponse findMemberAddress(Long memberAddressId) {
        return memberAddressRepository.findById(memberAddressId)
                .map(MemberAddressReadResponse::of)
                .orElseThrow();
    }

    public List<MemberAddressReadResponse> findMemberAddressByMemberId(Long memberId) {
        return memberAddressRepository.findAllByMemberId(memberId).stream()
                .map(MemberAddressReadResponse::of)
                .toList();
    }

    private DeliveryProcessor findDeliveryProcessor(ProcessDeliveryRequest request) {
        for (DeliveryProcessor processor : deliveryProcessor) {
            if (processor.isSupported(request.vendor())) {
                return processor;
            }
        }
        throw new IllegalArgumentException("Unsupported delivery vendor: " + request.vendor());
    }
}
