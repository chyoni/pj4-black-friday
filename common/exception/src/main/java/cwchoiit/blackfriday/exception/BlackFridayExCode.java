package cwchoiit.blackfriday.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum BlackFridayExCode {

    DOES_NOT_EXIST_PRODUCT(
            HttpStatus.NOT_FOUND,
            "BF-CATALOG-001",
            "Product does not exist. with productId = %s"
    ),
    INVALID_STOCK_COUNT(
            HttpStatus.BAD_REQUEST,
            "BF-CATALOG-002",
            "Invalid stock count. with productId = %s, current stockCount = %s, requested decrease count = %s"
    ),
    PRODUCT_SAVE_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "BF-CATALOG-003",
            "Save product failed."
    ),
    PRODUCT_REMOVED_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "BF-CATALOG-004",
            "Remove product failed."
    ),
    DOES_NOT_EXIST_DELIVERY(
            HttpStatus.NOT_FOUND,
            "BF-DELIVERY-001",
            "Delivery data does not exist. with deliveryId = %s"
    ),
    DOES_NOT_EXIST_MEMBER_ADDRESS(
            HttpStatus.NOT_FOUND,
            "BF-DELIVERY-002",
            "Member address data does not exist. with memberAddressId = %s"
    ),
    UNSUPPORTED_DELIVERY_VENDOR(
            HttpStatus.BAD_REQUEST,
            "BF-DELIVERY-003",
            "Unsupported delivery vendor. with vendor = %s"
    ),
    DOES_NOT_EXIST_MEMBER(
            HttpStatus.NOT_FOUND,
            "BF-MEMBER-001",
            "Member data does not exist. with memberId = %s"
    ),
    DOES_NOT_EXIST_PAYMENT_METHOD(
            HttpStatus.NOT_FOUND,
            "BF-PAYMENT-001",
            "Payment method data does not exist. with memberId = %s"
    ),
    DOES_NOT_EXIST_PAYMENT_METHOD_BY_PAYMENT_METHOD_ID(
            HttpStatus.NOT_FOUND,
            "BF-PAYMENT-002",
            "Payment method data does not exist. with paymentMethodId = %s"
    ),
    DOES_NOT_EXIST_PAYMENT(
            HttpStatus.NOT_FOUND,
            "BF-PAYMENT-003",
            "Payment data does not exist. with paymentId = %s"
    ),
    UNSUPPORTED_PAYMENT_METHOD(
            HttpStatus.BAD_REQUEST,
            "BF-PAYMENT-004",
            "Unsupported payment method. with paymentMethod = %s"
    ),
    INVALID_PAYMENT_METHOD(
            HttpStatus.BAD_REQUEST,
            "BF-PAYMENT-005",
            "Invalid payment method. with paymentMethod = %s"
    ),
    DOES_NOT_EXIST_PRODUCT_ORDER(
            HttpStatus.NOT_FOUND,
            "BF-PRODUCTORDER-001",
            "Product order data does not exist. with orderId = %s"
    ),
    INVALID_EVENT_TYPE(
            HttpStatus.BAD_REQUEST,
            "BF-EVENT-001",
            "Invalid event type. with eventType = %s"
    );

    private final HttpStatus status;
    private final String code;
    private final String reason;

    public BlackFridayException build() {
        return new BlackFridayException(status, code, reason);
    }

    public BlackFridayException build(Object... args) {
        return new BlackFridayException(status, code, reason.formatted(args));
    }
}
