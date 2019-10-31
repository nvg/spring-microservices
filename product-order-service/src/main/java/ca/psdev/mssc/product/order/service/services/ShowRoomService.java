package ca.psdev.mssc.product.order.service.services;

import ca.psdev.mssc.product.order.service.bootstrap.ProductOrderBootStrap;
import ca.psdev.mssc.product.order.service.domain.Customer;
import ca.psdev.mssc.product.order.service.repositories.ProductOrderRepository;
import ca.psdev.mssc.product.order.service.repositories.CustomerRepository;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderDto;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class ShowRoomService {

    private final CustomerRepository customerRepository;
    private final ProductOrderService productOrderService;
    private final ProductOrderRepository productOrderRepository;
    private final List<String> productUpcs = new ArrayList<>(3);

    public ShowRoomService(CustomerRepository customerRepository, ProductOrderService productOrderService,
                              ProductOrderRepository productOrderRepository) {
        this.customerRepository = customerRepository;
        this.productOrderService = productOrderService;
        this.productOrderRepository = productOrderRepository;

        productUpcs.add(ProductOrderBootStrap.PROD_1_UPC);
        productUpcs.add(ProductOrderBootStrap.PROD_2_UPC);
        productUpcs.add(ProductOrderBootStrap.PROD_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeShowRoomOrder(){
        log.info("Placing showroom order");

        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(ProductOrderBootStrap.SHOW_ROOM);

        if (customerList.size() == 1){ //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few show room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String productToOrder = getRandomProductUpc();

        log.info("Got product " + productToOrder);

        ProductOrderLineDto productOrderLine = ProductOrderLineDto.builder()
                .upc(productToOrder)
                .orderQuantity(new Random().nextInt(6)) //todo externalize value to property
                .build();

        log.info("Created DTO " + productOrderLine);

        List<ProductOrderLineDto> productOrderLineSet = new ArrayList<>();
        productOrderLineSet.add(productOrderLine);

        ProductOrderDto productOrder = ProductOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .productOrderLines(productOrderLineSet)
                .build();

        ProductOrderDto savedOrder = productOrderService.placeOrder(customer.getId(), productOrder);
        log.info("Saved Order " + savedOrder);

    }

    private String getRandomProductUpc() {
        return productUpcs.get(new Random().nextInt(productUpcs.size() -0));
    }
}
