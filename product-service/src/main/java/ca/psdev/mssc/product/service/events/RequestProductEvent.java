package ca.psdev.mssc.product.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newRequest")
@EqualsAndHashCode(callSuper = true)
public class RequestProductEvent extends ProductEvent {

	private static final long serialVersionUID = 1L;

	private int quantity;

	@Override
	public String toString() {
		return String.join(",",
				getProduct() == null ? "" : getProduct().getUuid().toString(),
				"" + quantity);
	}

}
