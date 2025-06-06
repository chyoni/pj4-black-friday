package cwchoiit.blackfriday.delivery.service;

import cwchoiit.blackfriday.delivery.adapter.DeliveryProcessor;
import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.MemberAddress;
import cwchoiit.blackfriday.delivery.repository.DeliverRepository;
import cwchoiit.blackfriday.delivery.repository.MemberAddressRepository;
import cwchoiit.blackfriday.delivery.service.request.CreateMemberAddressRequest;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.delivery.service.response.DeliveryReadResponse;
import cwchoiit.blackfriday.delivery.service.response.MemberAddressReadResponse;
import cwchoiit.blackfriday.event.payload.impl.DeliveryResponseEventPayload;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.DELIVERY_RESPONSE;
import static cwchoiit.blackfriday.exception.BlackFridayExCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final MemberAddressRepository memberAddressRepository;
    private final DeliverRepository deliverRepository;
    private final List<DeliveryProcessor> deliveryProcessor;
    private final OutboxEventPublisher outboxEventPublisher;

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

        Delivery delivery = deliverRepository.save(
                Delivery.create(
                        request.orderId(),
                        request.productName(),
                        request.productCount(),
                        request.address(),
                        request.vendor(),
                        referenceCode
                )
        );

        outboxEventPublisher.publish(
                DELIVERY_RESPONSE,
                new DeliveryResponseEventPayload(
                        delivery.getDeliveryId(),
                        delivery.getOrderId(),
                        delivery.getAddress(),
                        delivery.getProductName(),
                        delivery.getProductCount(),
                        delivery.getDeliveryStatus().name()
                )
        );

        return DeliveryReadResponse.of(delivery);
    }

    public DeliveryReadResponse findDelivery(Long deliveryId) {
        return deliverRepository.findById(deliveryId)
                .map(DeliveryReadResponse::of)
                .orElseThrow(() -> DOES_NOT_EXIST_DELIVERY.build(deliveryId));
    }

    public MemberAddressReadResponse findMemberAddress(Long memberAddressId) {
        return memberAddressRepository.findById(memberAddressId)
                .map(MemberAddressReadResponse::of)
                .orElseThrow(() -> DOES_NOT_EXIST_MEMBER_ADDRESS.build(memberAddressId));
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
        throw UNSUPPORTED_DELIVERY_VENDOR.build(request.vendor().name());
    }
}
