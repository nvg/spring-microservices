package ca.psdev.mssc.productservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ca.psdev.mssc.product.service.events.RequestProductEvent;
import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.mapper.ProductMapper;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class OrderingService {

	private static final String PRODUCT_REQUEST = "product-request";

	@Autowired
	private ProductRepo repo;

	// @Autowired
	private JmsTemplate jmsTemplate;
	
	@Scheduled()
	public void checkForLowInventory() {
		if (jmsTemplate == null) {
			return;
		}
		
		List<Product> products = repo.findLowStockProducts();
		products.forEach(p -> {
			log.debug("Requesting " + p);
			int delta = p.getMinQuantity() - p.getQuantity();

			RequestProductEvent e = RequestProductEvent.newRequest()
					.product(ProductMapper.INSTANCE.toProductDto(p))
					.quantity(delta)
					.build();

			try {
				jmsTemplate.convertAndSend(PRODUCT_REQUEST, e.toString());
			} catch (Exception ex) {
				log.info("Unable to send {} due to {}", e, ex);
			}
		});
	}

}
