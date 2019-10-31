package ca.psdev.mssc.product.order.service.web.mappers;

import ca.psdev.mssc.product.order.service.domain.ProductOrderLine;
import ca.psdev.mssc.product.order.service.web.model.ProductOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductOrderLineMapper {

    ProductOrderLineDto beerOrderLineToDto(ProductOrderLine line);

    ProductOrderLine dtoToProductOrderLine(ProductOrderLineDto dto);

}
