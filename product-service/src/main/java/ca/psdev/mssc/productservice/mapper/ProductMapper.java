package ca.psdev.mssc.productservice.mapper;

import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    Product toProduct(@MappingTarget Product base, ProductDto productDto);
}
