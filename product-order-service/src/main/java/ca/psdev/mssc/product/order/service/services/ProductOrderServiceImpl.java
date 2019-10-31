package ca.psdev.mssc.product.order.service.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.psdev.mssc.product.order.service.domain.Customer;
import ca.psdev.mssc.product.order.service.domain.OrderStatusEnum;
import ca.psdev.mssc.product.order.service.domain.ProductOrder;
import ca.psdev.mssc.product.order.service.repositories.CustomerRepository;
import ca.psdev.mssc.product.order.service.repositories.ProductOrderRepository;
import ca.psdev.mssc.product.order.service.web.mappers.ProductOrderMapper;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderDto;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderLineDto;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderPagedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

	private final ProductOrderRepository productOrderRepository;
	private final CustomerRepository customerRepository;
	private final ProductOrderMapper productOrderMapper;
	private final ApplicationEventPublisher publisher;

	@Autowired
	private ProductClient productClient;

	public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, CustomerRepository customerRepository,
			ProductOrderMapper productOrderMapper, ApplicationEventPublisher publisher) {
		this.productOrderRepository = productOrderRepository;
		this.customerRepository = customerRepository;
		this.productOrderMapper = productOrderMapper;
		this.publisher = publisher;
	}

	@Override
	public ProductOrderPagedList listOrders(UUID customerId, Pageable pageable) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (!customerOptional.isPresent()) {
			return null;
		}
		Page<ProductOrder> productOrderPage = productOrderRepository.findAllByCustomer(customerOptional.get(),
				pageable);

		return new ProductOrderPagedList(
				productOrderPage.stream().map(productOrderMapper::productOrderToDto).collect(Collectors.toList()),
				PageRequest.of(productOrderPage.getPageable().getPageNumber(),
						productOrderPage.getPageable().getPageSize()),
				productOrderPage.getTotalElements());
	}

	@Transactional
	@Override
	public ProductOrderDto placeOrder(UUID customerId, ProductOrderDto productOrderDto) {
		log.info("Placing order " + productOrderDto);

		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (!customerOptional.isPresent()) {
			throw new RuntimeException("Customer Not Found");
		}

		ProductOrder productOrder = productOrderMapper.dtoToProductOrder(productOrderDto);
		productOrder.setId(null); // should not be set by outside client
		productOrder.setCustomer(customerOptional.get());
		productOrder.setOrderStatus(OrderStatusEnum.NEW);

		productOrder.getProductOrderLines().forEach(line -> line.setProductOrder(productOrder));

		ProductOrder savedProductOrder = productOrderRepository.saveAndFlush(productOrder);

		log.debug("Saved Product Order: " + productOrder.getId());

		publisher.publishEvent(new NewProductOrderEvent(savedProductOrder));

		return productOrderMapper.productOrderToDto(savedProductOrder);
	}

	@Override
	public ProductOrderDto getOrderById(UUID customerId, UUID orderId) {
		ProductOrder productOrder = getOrder(customerId, orderId);
		ProductOrderDto result = productOrderMapper.productOrderToDto(productOrder);
		for (ProductOrderLineDto p : result.getProductOrderLines()) {
			String upc = p.getUpc();
			p.setProductDto(productClient.getProductByUpc(upc));
		}
		return productOrderMapper.productOrderToDto(productOrder);
	}

	@Override
	public void pickupOrder(UUID customerId, UUID orderId) {
		ProductOrder productOrder = getOrder(customerId, orderId);
		productOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

		productOrderRepository.save(productOrder);
	}

	private ProductOrder getOrder(UUID customerId, UUID orderId) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (!customerOptional.isPresent()) {
			throw new RuntimeException("Customer Not Found");
		}

		Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(orderId);

		if (!productOrderOptional.isPresent()) {
			throw new RuntimeException("Product Order Not Found");
		}

		ProductOrder productOrder = productOrderOptional.get();
		if (!productOrder.getCustomer().getId().equals(customerId)) {
			throw new RuntimeException("Invalid Product Customer");
		}
		return productOrder;
	}
}
