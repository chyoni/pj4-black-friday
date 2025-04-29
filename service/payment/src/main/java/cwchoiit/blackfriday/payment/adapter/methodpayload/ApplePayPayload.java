package cwchoiit.blackfriday.payment.adapter.methodpayload;

public record ApplePayPayload(Long amountKrw, String applePayWalletCode) implements Payload {
}
