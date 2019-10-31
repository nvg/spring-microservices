package ca.psdev.mssc.product.order.service.web.mappers;

import ca.psdev.mssc.product.order.service.domain.ProductOrder;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { DateMapper.class, ProductOrderLineMapper.class })
public interface ProductOrderMapper {

	@Mapping(target = "customerId", source = "customer.id")
	ProductOrderDto productOrderToDto(ProductOrder productOrder);

	ProductOrder dtoToProductOrder(ProductOrderDto dto);

}
