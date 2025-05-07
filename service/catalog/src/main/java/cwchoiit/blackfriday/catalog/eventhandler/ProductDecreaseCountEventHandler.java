package cwchoiit.blackfriday.catalog.eventhandler;

import cwchoiit.blackfriday.catalog.service.CatalogService;
import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.impl.ProductDecreaseCountEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDecreaseCountEventHandler implements EventHandler<ProductDecreaseCountEventPayload> {

    private final CatalogService catalogService;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.PRODUCT_COUNT_DECREASE;
    }

    @Override
    public void handle(Event<ProductDecreaseCountEventPayload> event) {
        ProductDecreaseCountEventPayload payload = event.getPayload();
        catalogService.decreaseStockCount(payload.getProductId(), payload.getDecreaseCount());
    }
}
