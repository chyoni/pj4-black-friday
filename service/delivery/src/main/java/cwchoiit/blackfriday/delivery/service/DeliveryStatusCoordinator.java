package cwchoiit.blackfriday.delivery.service;

import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.DeliveryStatus;
import cwchoiit.blackfriday.delivery.repository.DeliverRepository;
import cwchoiit.blackfriday.event.payload.impl.DeliveryResponseEventPayload;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.DELIVERY_RESPONSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryStatusCoordinator {

    private final DeliverRepository deliverRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void updateDeliveryStatus() {
        log.info("[updateDeliveryStatus] update delivery status by scheduler");
        List<Delivery> inDeliveries = deliverRepository.findAllByDeliveryStatus(DeliveryStatus.IN_DELIVERY);
        inDeliveries.forEach(delivery -> {
            delivery.updateDeliveryStatus(DeliveryStatus.COMPLETED);
            publishDeliveryResponseEvent(delivery, DeliveryStatus.COMPLETED);
        });

        List<Delivery> requestedDeliveries = deliverRepository.findAllByDeliveryStatus(DeliveryStatus.REQUESTED);
        requestedDeliveries.forEach(delivery -> {
            delivery.updateDeliveryStatus(DeliveryStatus.IN_DELIVERY);
            publishDeliveryResponseEvent(delivery, DeliveryStatus.IN_DELIVERY);
        });
    }

    private void publishDeliveryResponseEvent(Delivery delivery, DeliveryStatus deliveryStatus) {
        outboxEventPublisher.publish(
                DELIVERY_RESPONSE,
                new DeliveryResponseEventPayload(
                        delivery.getDeliveryId(),
                        delivery.getOrderId(),
                        delivery.getAddress(),
                        delivery.getProductName(),
                        delivery.getProductCount(),
                        deliveryStatus.name()
                )
        );
    }
}
