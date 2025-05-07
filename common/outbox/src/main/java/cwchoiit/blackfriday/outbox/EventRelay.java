package cwchoiit.blackfriday.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRelay {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> eventRelayKafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(Outbox outbox) {
        log.info("[createOutbox] Outbox = {}", outbox);
        outboxRepository.save(outbox);
    }

    @Async("eventRelayPublishEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(Outbox outbox) {
        publishEventProcess(outbox);
    }

    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "eventRelayPublishPendingEventExecutor"
    )
    public void publishPendingEvent() {
        outboxRepository.findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(
                LocalDateTime.now().minusSeconds(10),
                Pageable.ofSize(100)
        ).forEach(this::publishEventProcess);
    }

    private void publishEventProcess(Outbox outbox) {
        try {
            eventRelayKafkaTemplate.send(
                    outbox.getEventType().getTopic(),
                    outbox.getPayload()
            ).get(1, TimeUnit.SECONDS);
            outboxRepository.delete(outbox);
        } catch (Exception e) {
            log.error("[publishEventProcess] Failed to publish event. outbox: {}", outbox, e);
        }
    }
}
