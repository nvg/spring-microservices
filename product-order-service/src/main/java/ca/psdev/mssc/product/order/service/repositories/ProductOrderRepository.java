package ca.psdev.mssc.product.order.service.repositories;


import ca.psdev.mssc.product.order.service.domain.ProductOrder;
import ca.psdev.mssc.product.order.service.domain.Customer;
import ca.psdev.mssc.product.order.service.domain.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface ProductOrderRepository  extends JpaRepository<ProductOrder, UUID> {

    Page<ProductOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<ProductOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ProductOrder findOneById(UUID id);
}
