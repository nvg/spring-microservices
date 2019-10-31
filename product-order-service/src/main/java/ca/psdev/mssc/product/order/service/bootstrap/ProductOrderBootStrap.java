package ca.psdev.mssc.product.order.service.bootstrap;

import ca.psdev.mssc.product.order.service.domain.Customer;
import ca.psdev.mssc.product.order.service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductOrderBootStrap implements CommandLineRunner {
    public static final String SHOW_ROOM = "Show Room";
    public static final String PROD_1_UPC = "0631234200036";
    public static final String PROD_2_UPC = "0631234300019";
    public static final String PROD_3_UPC = "0083783375213";

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .customerName(SHOW_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build());
        }
    }
}
