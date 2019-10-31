package ca.psdev.mssc.product.inventory.service.web.mappers;

import ca.psdev.mssc.product.inventory.service.domain.ProductInventory;
import ca.psdev.mssc.product.inventory.service.web.model.ProductInventoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductInventoryMapper {

    ProductInventory productInventoryDtoToProductInventory(ProductInventoryDto productInventoryDTO);

    ProductInventoryDto productInventoryToProductInventoryDto(ProductInventory productInventory);
}
