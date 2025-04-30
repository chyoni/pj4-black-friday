package cwchoiit.blackfriday.delivery.repository;

import cwchoiit.blackfriday.delivery.entity.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
    List<MemberAddress> findAllByMemberId(Long memberId);
}
