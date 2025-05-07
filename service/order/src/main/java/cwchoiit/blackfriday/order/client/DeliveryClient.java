package cwchoiit.blackfriday.order.client;

import cwchoiit.blackfriday.order.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.order.service.response.DeliveryReadResponse;
import cwchoiit.blackfriday.order.service.response.MemberAddressReadResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryClient {
    private RestClient restClient;

    @Value("${client.delivery-service.url}")
    private String deliveryUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(deliveryUrl);
    }

    public ApiResponse<MemberAddressReadResponse> findMemberAddress(Long memberAddressId) {
        return restClient.get()
                .uri("/api/v1/delivery/addresses/{memberAddressId}", memberAddressId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ApiResponse<List<MemberAddressReadResponse>> findAllMemberAddressByMemberId(Long memberId) {
        return restClient.get()
                .uri("/api/v1/delivery/addresses/member/{memberId}", memberId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ApiResponse<DeliveryReadResponse> processDelivery(Long orderId,
                                                             String productName,
                                                             Long productCount,
                                                             String address,
                                                             ProcessDeliveryRequest.DeliveryVendor vendor) {
        return restClient.post()
                .uri("/api/v1/delivery/process-delivery")
                .body(new ProcessDeliveryRequest(orderId, productName, productCount, address, vendor))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ApiResponse<DeliveryReadResponse> findDelivery(Long deliveryId) {
        return restClient.get()
                .uri("/api/v1/delivery/{deliveryId}", deliveryId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
