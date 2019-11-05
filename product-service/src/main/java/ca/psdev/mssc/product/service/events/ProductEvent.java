package ca.psdev.mssc.product.service.events;

import java.io.Serializable;

import ca.psdev.mssc.productservice.web.model.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProductDto product;

}
