package ca.psdev.mssc.product.order.service.services;

import ca.psdev.mssc.product.order.service.web.model.ProductOrderDto;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductOrderService {
	
	ProductOrderPagedList listOrders(UUID customerId, Pageable pageable);

	ProductOrderDto placeOrder(UUID customerId, ProductOrderDto beerOrderDto);

	ProductOrderDto getOrderById(UUID customerId, UUID orderId);

	void pickupOrder(UUID customerId, UUID orderId);
}
