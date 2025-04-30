package cwchoiit.blackfriday.delivery.service;

import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.DeliveryStatus;
import cwchoiit.blackfriday.delivery.repository.DeliverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryStatusCoordinator {

    private final DeliverRepository deliverRepository;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void updateDeliveryStatus() {
        log.info("[updateDeliveryStatus] update delivery status by scheduler");
        List<Delivery> inDeliveries = deliverRepository.findAllByDeliveryStatus(DeliveryStatus.IN_DELIVERY);
        inDeliveries.forEach(delivery -> delivery.updateDeliveryStatus(DeliveryStatus.COMPLETED));

        List<Delivery> requestedDeliveries = deliverRepository.findAllByDeliveryStatus(DeliveryStatus.REQUESTED);
        requestedDeliveries.forEach(delivery -> delivery.updateDeliveryStatus(DeliveryStatus.IN_DELIVERY));
    }
}
