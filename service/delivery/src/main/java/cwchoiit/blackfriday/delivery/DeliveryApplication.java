package cwchoiit.blackfriday.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * {@code @EntityScan}, {@code @EnableJpaRepositories}는 {@code common} 모듈안에 있는 {@code outbox}를
 * 의존성으로 추가하면, 저 모듈안에 엔티티({@code Outbox})랑 레포지토리({@code OutboxRepository})가 있는데 이 친구들을 스캔해주기 위해서
 * 스캔 범위를 {@code common} 모듈도 해당되게 설정
 */
@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "cwchoiit.blackfriday")
@EnableJpaRepositories(basePackages = "cwchoiit.blackfriday")
public class DeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }
}
