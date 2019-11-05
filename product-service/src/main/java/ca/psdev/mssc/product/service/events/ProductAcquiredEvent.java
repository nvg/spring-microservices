package ca.psdev.mssc.product.service.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductAcquiredEvent extends ProductEvent {

	private static final long serialVersionUID = 1L;

	private int quantity;
	
}
