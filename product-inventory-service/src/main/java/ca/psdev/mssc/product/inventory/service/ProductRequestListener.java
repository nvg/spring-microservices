package ca.psdev.mssc.product.inventory.service;

import java.util.UUID;

import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductRequestListener {
	
	@JmsListener(destination = "product-request")
	public void listen(TextMessage e) throws Exception {
		String[] s = e.getText().split(",");
		UUID productUuid = UUID.fromString(s[0]);
		int productCount = Integer.parseInt(s[1]);
		
		log.info("Requesting {} qty {}", productUuid, productCount);
	}

}
