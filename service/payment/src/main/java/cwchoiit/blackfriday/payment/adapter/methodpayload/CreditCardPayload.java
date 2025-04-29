package cwchoiit.blackfriday.payment.adapter.methodpayload;

public record CreditCardPayload(Long amountKrw, String creditCardNumber) implements Payload {
}
