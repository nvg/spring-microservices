package ca.psdev.mssc.product.order.service.services;

import org.springframework.context.ApplicationEvent;

import ca.psdev.mssc.product.order.service.domain.ProductOrder;

public class NewProductOrderEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public NewProductOrderEvent(ProductOrder productOrder) {
		super(productOrder);
	}

}
